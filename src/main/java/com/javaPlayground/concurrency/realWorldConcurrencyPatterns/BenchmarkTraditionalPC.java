package com.javaPlayground.concurrency.realWorldConcurrencyPatterns;

import java.util.concurrent.*;

public class BenchmarkTraditionalPC {

    static final int TASKS = 200_000;

    public static void main(String[] args) throws Exception {
        BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(1000);
        ExecutorService pool = new ThreadPoolExecutor(
                8, 8, 0, TimeUnit.SECONDS, queue
        );

        long start = System.nanoTime();

        CountDownLatch latch = new CountDownLatch(TASKS);

        for (int i = 0; i < TASKS; i++) {
            pool.submit(() -> {
                latch.countDown();
            });
        }

        latch.await();
        long elapsed = System.nanoTime() - start;

        System.out.println("Traditional PC Throughput: " +
                (TASKS * 1_000_000_000L / elapsed) + " ops/sec");

        pool.shutdown();
    }
}

