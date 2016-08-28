package ru.sbt.cacheproxy.proxy;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by Никита on 24.08.2016.
 */

@Retention(RUNTIME)
@Target(METHOD)

public @interface Cache {

    CacheType cacheType() default CacheType.IN_MEMORY;

    String fileNamePrefix() default "";

    boolean zip() default false;

    Class[] identityBy() default {};

    int maxSizeOfList() default 10_000;

}
