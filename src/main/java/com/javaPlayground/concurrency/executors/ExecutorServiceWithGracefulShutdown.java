package com.javaPlayground.concurrency.executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExecutorServiceWithGracefulShutdown {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(4);

        for (int i = 1; i <= 20; i++) {
            int taskId = i;
            pool.submit(() -> {
                System.out.println("Running task " + taskId +
                        " on " + Thread.currentThread().getName());

                try { Thread.sleep(500); } catch (InterruptedException ignored) {}
            });
        }

        pool.shutdown();                           // no more tasks allowed
        System.out.println("Waiting for tasks...");

        if (!pool.awaitTermination(10, TimeUnit.SECONDS)) {
            System.out.println("Forcing shutdown...");
            pool.shutdownNow();
        }

        System.out.println("Finished.");
    }
}

