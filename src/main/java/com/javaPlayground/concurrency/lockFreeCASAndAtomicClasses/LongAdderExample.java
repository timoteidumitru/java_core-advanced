package com.javaPlayground.concurrency.lockFreeCASAndAtomicClasses;

import java.util.concurrent.atomic.LongAdder;

public class LongAdderExample {

    private final LongAdder requestCount = new LongAdder();

    public void recordRequest() {
        requestCount.increment();
    }

    public long totalRequests() {
        return requestCount.sum();
    }

    public static void main(String[] args) throws InterruptedException {
        LongAdderExample metrics = new LongAdderExample();

        Runnable simulateRequests = () -> {
            for (int i = 0; i < 1_000_000; i++) {
                metrics.recordRequest();
            }
        };

        Thread t1 = new Thread(simulateRequests);
        Thread t2 = new Thread(simulateRequests);
        Thread t3 = new Thread(simulateRequests);
        Thread t4 = new Thread(simulateRequests);

        long start = System.currentTimeMillis();

        t1.start(); t2.start(); t3.start(); t4.start();
        t1.join(); t2.join(); t3.join(); t4.join();

        long end = System.currentTimeMillis();

        System.out.println("Total requests = " + metrics.totalRequests());
        System.out.println("Time = " + (end - start) + " ms");
    }
}
