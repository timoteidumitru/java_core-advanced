package com.javaPlayground.concurrency.CPUvsIoBound;

import java.util.concurrent.*;

public class IoBoundFixedPoolExample {

    static final int TASKS = 10_000;

    public static void main(String[] args) throws Exception {

        ExecutorService pool = Executors.newFixedThreadPool(100);

        long start = System.nanoTime();

        for (int i = 0; i < TASKS; i++) {
            pool.submit(IoBoundFixedPoolExample::blockingIo);
        }

        pool.shutdown();
        pool.awaitTermination(5, TimeUnit.MINUTES);

        long end = System.nanoTime();

        System.out.println("I/O-bound (Fixed Pool ❌)");
        System.out.println("Time: " + (end - start) / 1_000_000 + " ms");
    }

    static void blockingIo() {
        try {
            Thread.sleep(10);
        } catch (InterruptedException ignored) {}
    }
}

