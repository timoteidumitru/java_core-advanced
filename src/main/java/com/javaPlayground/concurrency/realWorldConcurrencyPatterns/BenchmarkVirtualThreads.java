package com.javaPlayground.concurrency.realWorldConcurrencyPatterns;

import java.util.concurrent.*;

public class BenchmarkVirtualThreads {

    static final int TASKS = 10_000_000;

    public static void main(String[] args) throws Exception {

        ExecutorService vt = Executors.newVirtualThreadPerTaskExecutor();

        long start = System.nanoTime();

        CountDownLatch latch = new CountDownLatch(TASKS);

        for (int i = 0; i < TASKS; i++) {
            vt.submit(latch::countDown);
        }

        latch.await();
        long elapsed = System.nanoTime() - start;

        System.out.println("Virtual Threads Throughput: " +
                (TASKS * 1_000_000_000L / elapsed) + " ops/sec");

        vt.shutdown();
    }
}

