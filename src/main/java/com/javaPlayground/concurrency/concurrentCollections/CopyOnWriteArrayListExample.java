package com.javaPlayground.concurrency.concurrentCollections;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteArrayListExample {

    public static void main(String[] args) {
        List<String> listeners = new CopyOnWriteArrayList<>();

        listeners.add("Listener1");
        listeners.add("Listener2");

        for (String l : listeners) {
            System.out.println("Firing event to: " + l);
        }

        // Safe to add while iterating
        listeners.add("Listener3");

        System.out.println("Current: " + listeners);
    }
}
