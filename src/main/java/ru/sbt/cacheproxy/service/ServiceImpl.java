package ru.sbt.cacheproxy.service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Никита on 28.08.2016.
 */
public class ServiceImpl implements Service {
    @Override
    public List<Object> run(String name, int amount, boolean isSmth) {
        List<Object> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        list.add("6");
        list.add("7");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException("InterruptedException" ,e);
        }
        return list;
    }

    @Override
    public Object work(int id) {
        return "smth";
    }
}
