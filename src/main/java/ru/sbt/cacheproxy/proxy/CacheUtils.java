package ru.sbt.cacheproxy.proxy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static ru.sbt.cacheproxy.proxy.CacheType.IN_MEMORY;

/**
 * Created by Никита on 25.08.2016.
 */
public class CacheUtils {

    private final String rootPath;
    private final Map<Object, Object> inMemoryStorage = new HashMap<>();

    public CacheUtils(String rootPath) {
        this.rootPath = rootPath;
    }

    public Object invokeAndCached(Object delegate, Method method, Object[] args) {
        return method.getAnnotation(Cache.class).cacheType() == IN_MEMORY ? invokeAndCachedInMemory(delegate, method, args) : invokeAndCachedInFile(delegate, method, args);
    }

    private Object invokeAndCachedInFile(Object delegate, Method method, Object[] args) {

        return null;
    }

/*    public static Object checkItemsAmountToCache(Method method, Object value) {
        if (method.getReturnType().equals(List.class)) {
            List<Object> result = (List<Object>) value;
            int toIndex = method.getAnnotation(Cache.class).maxSizeOfList();
            if (toIndex < result.size())
                result = new ArrayList<>(result.subList(0, toIndex));
            return result;
        } else
            return value;
    }*/

    public static String generateFileName(Method method, Object[] args, String rootPath) {
        String prefix = method.getAnnotation(Cache.class).fileNamePrefix();
        if ("".equals(prefix)) prefix = method.getName();
        StringBuilder sb = new StringBuilder();
        sb.append(prefix)
                .append("_");

        for (Object arg : args) {
            sb.append(arg).append("_");
        }

        sb.replace(sb.lastIndexOf("_"), sb.lastIndexOf("_"), ".ser");

        String fileName = sb.toString();
        fileName = fileName.substring(0, fileName.lastIndexOf("_"));

        return rootPath + fileName;
    }

    private Object invokeAndCachedInMemory(Object delegate, Method method, Object[] args) {
        if (!inMemoryStorage.containsKey(key(method, getIdentityArgs(method, args)))) {
            try {
                Object result = method.invoke(delegate, args);
                inMemoryStorage.put(key(method, getIdentityArgs(method, args)), result);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException("Exception from proxy: IllegalAccessException or InvocationTargetException when invoke method ", e);
            }
        }
        return inMemoryStorage.get(key(method, getIdentityArgs(method, args)));
    }

    private Object[] getIdentityArgs(Method method, Object[] args) {
        if (method.getAnnotation(Cache.class).identityBy().length == 0) return args;
        Object[] identityArgs = null;
        int i = -1;
        for (Object arg : args) {
            if (asList(method.getAnnotation(Cache.class).identityBy()).contains(arg.getClass())) {
                identityArgs[++i] = arg;
            }
        }
        return identityArgs;
    }

    private Object key(Method method, Object[] args) {
        List<Object> key = new ArrayList<>();
        key.add(method);
        key.addAll(asList(args));
        return key;
    }

}
