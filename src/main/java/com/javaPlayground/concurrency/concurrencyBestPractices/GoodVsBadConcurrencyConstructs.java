package com.javaPlayground.concurrency.concurrencyBestPractices;

import java.util.ArrayList;
import java.util.List;

public class GoodVsBadConcurrencyConstructs {

    // -------------------------------------------
    // BAD: Shared mutable ArrayList (not thread-safe!)
    // -------------------------------------------
    public static class BadDesignApproach {
        private final List<String> list = new ArrayList<>();

        public void add(String value) {
            list.add(value);  // UNSAFE
        }

        public List<String> getList() {
            return list;
        }
    }

    // -------------------------------------------
    // GOOD: Immutable snapshot copy pattern
    // -------------------------------------------
    public static class GoodDesignApproach {

        // Pure function; no shared mutable state
        public List<String> addToNewList(List<String> original, String val) {
            List<String> copy = new ArrayList<>(original);
            copy.add(val);
            return List.copyOf(copy);  // immutable snapshot
        }
    }

    // -------------------------------------------
    // Test / Benchmark Runner
    // -------------------------------------------
    public static void main(String[] args) throws Exception {
        runBadExample();
        System.out.println("\n---------------------------------\n");
        runGoodExample();
    }

    // -------------------------------------------
    // Test Unsafe Version
    // -------------------------------------------
    private static void runBadExample() throws InterruptedException {
        System.out.println("=== Running BAD Design Test ===");

        BadDesignApproach bad = new BadDesignApproach();

        int THREADS = 100;
        int OPERATIONS = 1000;

        Thread[] arr = new Thread[THREADS];

        for (int t = 0; t < THREADS; t++) {
            arr[t] = new Thread(() -> {
                for (int i = 0; i < OPERATIONS; i++) {
                    bad.add(Thread.currentThread().getName() + "-" + i);
                }
            });
            arr[t].start();
        }

        for (Thread t : arr) t.join();

        int expected = THREADS * OPERATIONS;
        int actual = bad.getList().size();

        System.out.println("Expected size = " + expected);
        System.out.println("Actual size   = " + actual);
        System.out.println("Correct?      = " + (expected == actual));
    }

    // -------------------------------------------
    // Test Safe Version
    // -------------------------------------------
    private static void runGoodExample() throws InterruptedException {
        System.out.println("=== Running GOOD Design Test ===");

        GoodDesignApproach good = new GoodDesignApproach();

        // shared reference, but immutable list => safe
        List<String> currentList = List.of();

        int THREADS = 100;
        int OPERATIONS = 1000;

        // threads need a shared holder
        final List<String>[] shared = new List[]{currentList};

        Thread[] arr = new Thread[THREADS];

        for (int t = 0; t < THREADS; t++) {
            arr[t] = new Thread(() -> {
                for (int i = 0; i < OPERATIONS; i++) {
                    synchronized (shared) {
                        shared[0] = good.addToNewList(
                                shared[0],
                                Thread.currentThread().getName() + "-" + i
                        );
                    }
                }
            });
            arr[t].start();
        }

        for (Thread t : arr) t.join();

        int expected = THREADS * OPERATIONS;
        int actual = shared[0].size();

        System.out.println("Expected size = " + expected);
        System.out.println("Actual size   = " + actual);
        System.out.println("Correct?      = " + (expected == actual));
    }
}
