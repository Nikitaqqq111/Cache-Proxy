package ru.sbt.cacheproxy.storage;

import ru.sbt.cacheproxy.generation.GenerationUtils;
import ru.sbt.cacheproxy.proxy.Cache;
import ru.sbt.cacheproxy.serialization.SerializationException;
import ru.sbt.cacheproxy.serialization.SerializationUtils;

import java.io.File;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static ru.sbt.cacheproxy.proxy.CacheType.IN_MEMORY;

/**
 * Created by Никита on 28.08.2016.
 */
public class Storage {
    private final String rootPath;
    private final Map<Object, Object> inMemoryStorage = new HashMap<Object, Object>();

    public Storage(String rootPath) {
        this.rootPath = rootPath;
    }

    public Object invokeAndCached(Object delegate, Method method, Object[] args) throws SerializationException {
        return method.getAnnotation(Cache.class).cacheType() == IN_MEMORY ? invokeAndCachedInMemory(delegate, method, args) : invokeAndCachedInFile(delegate, method, args);
    }

    private Object invokeAndCachedInFile(Object delegate, Method method, Object[] args) throws SerializationException {
        String fileName = GenerationUtils.generateFileName(method, args, rootPath);
        String zipFileName = fileName.substring(0, fileName.lastIndexOf(".")) + ".zip";
        if (!new File(fileName).exists() && !new File(zipFileName).exists()) {
            try {
                Object result = method.invoke(delegate, args);
                result = GenerationUtils.cutList(method, result);
                SerializationUtils.serialize((Serializable) result, fileName);
                if (method.getAnnotation(Cache.class).zip()) SerializationUtils.zipFile(zipFileName);
                return result;
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException("IllegalAccessException | InvocationTargetException, caused by: method.invoke(delegate, args), from: Storage", e);
            }
        }
        return SerializationUtils.deserialize(fileName);

    }

    private Object invokeAndCachedInMemory(Object delegate, Method method, Object[] args) {
        if (!inMemoryStorage.containsKey(GenerationUtils.generateKey(method, args))) {
            try {
                Object result = GenerationUtils.cutList(method, method.invoke(delegate, args));
                inMemoryStorage.put(GenerationUtils.generateKey(method, args), result);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException("IllegalAccessException | InvocationTargetException, caused by: method.invoke(delegate, args), from: Storage", e);
            }
        }
        return inMemoryStorage.get(GenerationUtils.generateKey(method, args));
    }

}
