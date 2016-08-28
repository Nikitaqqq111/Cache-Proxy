package ru.sbt.cacheproxy.proxy;

import ru.sbt.cacheproxy.storage.Storage;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import static java.lang.ClassLoader.getSystemClassLoader;

/**
 * Created by Никита on 25.08.2016.
 */
public class CachedInvocationHandler implements InvocationHandler {

    private final Object delegate;

    private final Storage storage;

    public static <T> T cache(Object delegate, String rootPath) {
        return (T) Proxy.newProxyInstance(getSystemClassLoader(),
                delegate.getClass().getInterfaces(),
                new CachedInvocationHandler(delegate, rootPath)
        );
    }

    public CachedInvocationHandler(Object delegate, String rootPath) {
        this.delegate = delegate;
        this.storage = new Storage(rootPath);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (!method.isAnnotationPresent(Cache.class)) return invoke(method, args);
        return storage.invokeAndCached(delegate, method, args);
    }

    private Object invoke(Method method, Object[] args) throws Throwable {
        try {
            return method.invoke(delegate, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("IllegalAccessException | InvocationTargetException, caused by: method.invoke(delegate, args), from: CachedInvocationHandler", e);
        }
    }

}
