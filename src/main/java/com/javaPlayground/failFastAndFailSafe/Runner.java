package com.javaPlayground.failFastAndFailSafe;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class Runner {
    public static void main(String[] args) throws InterruptedException {
        usingArrayList();
//        usingHasMap();
    }

    public static void usingArrayList() throws InterruptedException {
//        List<String> list = new ArrayList<>(); // fail fast
        List<String> list = new CopyOnWriteArrayList<>(); // fail safe
        list.add("a");
        list.add("b");
        list.add("c");

        Iterator<String> iterator = list.iterator();

        while (iterator.hasNext()){
            String elem = iterator.next(); // on fail-safe modCount will modify and will copy array before go to next iteration
            System.out.println(elem);
            list.add("d");
            Thread.sleep(1000);
        }

        System.out.println(list);
    }

    public static void usingHasMap() throws InterruptedException {
//        Map<Integer, String> map = new HashMap<>(); // fail-fast
        Map<Integer, String> map = new ConcurrentHashMap<>(); // fail-safe
        map.put(1, "Mike");
        map.put(2, "Jane");

        Iterator<String> iterator = map.values().iterator();

        while (iterator.hasNext()){
            String elem = iterator.next(); // no copy/clone concept on maps
            System.out.println(elem);
            map.put(3, "John");
            Thread.sleep(1000);
        }
        System.out.println(map);
    }
}
