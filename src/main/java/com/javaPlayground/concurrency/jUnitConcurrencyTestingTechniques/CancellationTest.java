package com.javaPlayground.concurrency.jUnitConcurrencyTestingTechniques;

import java.util.concurrent.*;

public class CancellationTest {

    public static void main(String[] args) throws Exception {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        Future<?> task = executor.submit(() -> {
            try {
                while (true) {
                    System.out.println("Working...");
                    Thread.sleep(200);
                }
            } catch (InterruptedException e) {
                System.out.println("Task interrupted, cleaning up");
            }
        });

        Thread.sleep(700);
        task.cancel(true);

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.SECONDS);
        System.out.println("Shutdown completed");
    }
}

