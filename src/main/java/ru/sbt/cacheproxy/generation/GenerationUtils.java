package ru.sbt.cacheproxy.generation;

import ru.sbt.cacheproxy.proxy.Cache;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

/**
 * Created by Никита on 28.08.2016.
 */
public class GenerationUtils {

    public static Object[] getIdentityArguments(Method method, Object[] args) {
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

    public static String generateFileName(Method method, Object[] args, String rootPath) {
        String prefix = method.getAnnotation(Cache.class).fileNamePrefix();
        if ("".equals(prefix)) prefix = method.getName();
        StringBuilder sb = new StringBuilder();
        sb.append(prefix)
                .append("_");
        for (Object arg : getIdentityArguments(method, args)) {
            sb.append(arg).append("_");
        }
        sb.replace(sb.lastIndexOf("_"), sb.lastIndexOf("_"), ".ser");
        String fileName = sb.toString();
        fileName = fileName.substring(0, fileName.lastIndexOf("_"));
        return rootPath + fileName;
    }

    public static Object generateKey(Method method, Object[] args) {
        List<Object> key = new ArrayList<>();
        key.add(method);
        key.addAll(asList(getIdentityArguments(method, args)));
        return key;
    }

    public static Object cutList(Method method, Object result) {
        if (!(result instanceof List)) return result;
        List<Object> cutResult = (List<Object>) result;
        int toIndex = method.getAnnotation(Cache.class).maxSizeOfList();
        if (toIndex < cutResult.size())
            result = new ArrayList<>(cutResult.subList(0, toIndex));
        return result;
    }

}
