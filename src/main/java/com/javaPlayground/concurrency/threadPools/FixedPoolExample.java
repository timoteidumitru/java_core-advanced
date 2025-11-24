package com.javaPlayground.concurrency.threadPools;

import java.util.concurrent.*;

public class FixedPoolExample {

    public static void main(String[] args) throws InterruptedException {

        ExecutorService pool = Executors.newFixedThreadPool(3);

        Runnable task = () -> {
            System.out.println(Thread.currentThread().getName() + " is working...");
            try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
        };

        for (int i = 1; i <= 10; i++) {
            pool.submit(task);
        }

        pool.shutdown();
        pool.awaitTermination(10, TimeUnit.SECONDS);
    }
}

