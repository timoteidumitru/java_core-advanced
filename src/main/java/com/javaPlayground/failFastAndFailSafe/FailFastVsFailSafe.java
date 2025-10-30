package com.javaPlayground.failFastAndFailSafe;

import java.util.*;
import java.util.concurrent.*;

public class FailFastVsFailSafe {
    public static void main(String[] args) {
//        System.out.println("=== Fail-Fast Example with ArrayList ===");
//        failFastListExample();

//        System.out.println("\n=== Fail-Safe Example with CopyOnWriteArrayList ===");
//        failSafeListExample();
//
//        System.out.println("\n=== Fail-Fast Example with HashMap ===");
//        failFastMapExample();
//
        System.out.println("\n=== Fail-Safe Example with ConcurrentHashMap ===");
        failSafeMapExample();
    }

    // -------------------- FAIL-FAST --------------------
    private static void failFastListExample() {
        List<String> list = new ArrayList<>();
        list.add("A");
        list.add("B");
        list.add("C");

        try {
            for (String item : list) {
                System.out.println(item);
                // This will cause ConcurrentModificationException
                if (item.equals("B")) {
                    list.add("D");
                }
            }
        } catch (ConcurrentModificationException e) {
            System.out.println("❌ ConcurrentModificationException caught: " + e);
        }
    }

    private static void failFastMapExample() {
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "Apple");
        map.put(2, "Banana");
        map.put(3, "Cherry");

        try {
            for (Integer key : map.keySet()) {
                System.out.println(key + " = " + map.get(key));
                // Causes ConcurrentModificationException
                if (key == 2) {
                    map.put(4, "Date");
                }
            }
        } catch (ConcurrentModificationException e) {
            System.out.println("❌ ConcurrentModificationException caught: " + e);
        }
    }

    // -------------------- FAIL-SAFE --------------------
    private static void failSafeListExample() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        list.add("A");
        list.add("B");
        list.add("C");

        for (String item : list) {
            System.out.println(item);
            // Safe modification — works on a copy
            if (item.equals("B")) {
                list.add("D");
            }
        }

        System.out.println("✅ Final list: " + list);
    }

    private static void failSafeMapExample() {
        ConcurrentHashMap<Integer, String> map = new ConcurrentHashMap<>();
        map.put(1, "Apple");
        map.put(2, "Banana");
        map.put(3, "Cherry");

        for (Integer key : map.keySet()) {
            System.out.println(key + " = " + map.get(key));
            // Safe modification — works on internal copy
            if (key == 2) {
                map.put(4, "Date");
            }
        }

        System.out.println("✅ Final map: " + map);
    }
}

