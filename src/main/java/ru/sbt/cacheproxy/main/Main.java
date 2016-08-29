package ru.sbt.cacheproxy.main;

import ru.sbt.cacheproxy.service.Service;
import ru.sbt.cacheproxy.service.ServiceImpl;

import static ru.sbt.cacheproxy.proxy.CachedInvocationHandler.cache;

/**
 * Created by Никита on 28.08.2016.
 */
public class Main {

    public static void main(String[] args) throws NoSuchMethodException {
        Service service = cache(new ServiceImpl(), "C:\\Users\\Никита\\IdeaProjects\\cache-proxy\\src\\test\\resources\\");
        System.out.println(service.run("qqqqqq", 123, true));
        System.out.println(service.run("qqqqqq", 544, true));
        System.out.println(service.run("qqqqqq", 1, false));
        System.out.println(service.run("qqqqqq", 12, true));
        System.out.println(service.run("qqqqqq", 13, false));
        System.out.println(service.run("qqq", 123, true));
        System.out.println(service.work(15));
        System.out.println(service.work(15));
        System.out.println(service.work(15));
        System.out.println(service.work(15));
        System.out.println(service.work(17));
        System.out.println(service.work(18));


    }

}
