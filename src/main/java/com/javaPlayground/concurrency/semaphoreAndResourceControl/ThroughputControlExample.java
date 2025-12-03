package com.javaPlayground.concurrency.semaphoreAndResourceControl;

import java.util.concurrent.*;

public class ThroughputControlExample {

    private static final int MAX_CONCURRENT_TASKS = 3; // limit
    private static final Semaphore SEMAPHORE = new Semaphore(MAX_CONCURRENT_TASKS);

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(10);

        for (int i = 1; i <= 20; i++) {
            final int taskId = i;

            executor.submit(() -> {
                try {
                    SEMAPHORE.acquire(); // acquire a permit
                    System.out.println("Task " + taskId + " STARTED. Available permits: " + SEMAPHORE.availablePermits());

                    // Simulate heavy work
                    Thread.sleep((long)(Math.random() * 1500 + 500));

                    System.out.println("Task " + taskId + " FINISHED");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    SEMAPHORE.release(); // always release
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);
        System.out.println("All tasks complete.");
    }
}

