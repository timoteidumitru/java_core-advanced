package com.javaPlayground.failFastAndFailSafe;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HashMapWithThread extends Thread {
    // will throw an ConcurrentModificationException due to Locking Mechanism on whole Object
//     public static Map<Integer, String> map = new HashMap<>();
    // will NOT throw any ConcurrentModificationException due to Locking Mechanism of accessing just one element of the Object
    public static Map<Integer, String> map = new ConcurrentHashMap<>();
    public static void main(String[] args) throws InterruptedException {
        map.put(1, "First");
        map.put(2, "Second");
        map.put(3, "Third");

        HashMapWithThread customThread = new HashMapWithThread();
        customThread.start();

        for (Object m : map.entrySet()){
            System.out.println(m);
            Thread.sleep(1000);
        }
        System.out.println(map);
    }

    @Override
    public void run(){
        try {
            Thread.sleep(100);
            map.put(4, "Fourth");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
