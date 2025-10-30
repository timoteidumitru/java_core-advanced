package com.javaPlayground.failFastAndFailSafe;

import java.util.concurrent.*;

/*
    A ConcurrentHashMap iterator walks through buckets in real time.
    If a bucket changes before the iterator visits it, the change appears.
    If it changes after, the iterator never sees it — hence “possibly (live view).”
*/

public class WeaklyConsistentIterator {

    public static void main(String[] args) {
        ConcurrentHashMap<Integer, String> map = new ConcurrentHashMap<>();
        map.put(1, "A");
        map.put(2, "B");
        map.put(3, "C");

        // Start a thread that will add elements while we iterate
        new Thread(() -> {
            try {
                Thread.sleep(500); // delay to let iteration start
                map.put(4, "D");
                map.put(5, "E");
                System.out.println("Writer added new entries");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();

        // Main thread iterates over the map
        for (Integer key : map.keySet()) {
            System.out.println("Reader sees key: " + key);
            try {
                Thread.sleep(1000); // slow down iteration
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        System.out.println("Final map content: " + map);
    }
}

