package com.javaPlayground.concurrency.concurrencyBestPractices;

import java.util.concurrent.atomic.AtomicInteger;

public class AvoidingSharedMutableState {

    public static class BadCounter {
        private int value = 0; // mutable, shared, unsafe

        public void increment() {
            int temp = value;
            try { Thread.sleep(0, 10); } catch (Exception ignored) {}  // 1 nanosecond
            value = temp + 1;
        }

        public int getValue() {
            return value;
        }
    }

    public static class SafeCounter {
        private final AtomicInteger value = new AtomicInteger(0);

        public void increment() {
            value.incrementAndGet(); // atomic & thread-safe
        }

        public int getValue() {
            return value.get();
        }
    }

    public static void main(String[] args) throws InterruptedException {
//         BadCounter counter = new BadCounter();
        SafeCounter counter = new SafeCounter();

        int THREADS = 1000;
        Thread[] arr = new Thread[THREADS];

        // Each thread increments counter once
        for (int i = 0; i < THREADS; i++) {
            arr[i] = new Thread(counter::increment);
            arr[i].start();
        }

        for (Thread t : arr) t.join();

        System.out.println("Expected value = " + THREADS);
        System.out.println("Actual value   = " + counter.getValue());
    }

}
