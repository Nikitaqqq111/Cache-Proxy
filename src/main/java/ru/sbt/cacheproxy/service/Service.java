package ru.sbt.cacheproxy.service;

import ru.sbt.cacheproxy.proxy.Cache;
import ru.sbt.cacheproxy.proxy.CacheType;

import java.util.List;

/**
 * Created by Никита on 28.08.2016.
 */
public interface Service {

    @Cache(fileNamePrefix = "smth", identityBy = {String.class, double.class}, maxSizeOfList = 3)
    List<Object> run(String name, int amount, boolean isSmth);

    @Cache(cacheType = CacheType.FILE, zip = true, maxSizeOfList = 2)
    String work(int id);

}
