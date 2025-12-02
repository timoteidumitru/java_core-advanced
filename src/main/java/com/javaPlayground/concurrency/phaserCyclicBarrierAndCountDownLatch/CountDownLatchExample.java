package com.javaPlayground.concurrency.phaserCyclicBarrierAndCountDownLatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CountDownLatchExample {
    public static void main(String[] args) throws InterruptedException {
        int services = 5;

        CountDownLatch latch = new CountDownLatch(services);
        ExecutorService pool = Executors.newFixedThreadPool(services);

        for (int i = 1; i <= services; i++) {
            int id = i;
            pool.submit(() -> {
                try {
                    System.out.println("Starting service " + id + "...");
                    Thread.sleep(500 + id * 300);
                    System.out.println("Service " + id + " READY");
                } catch (InterruptedException ignored) {}
                finally {
                    latch.countDown();
                }
            });
        }

        System.out.println("Main waiting for all services...");
        latch.await();
        System.out.println("🔥 All services started. System READY.");

        pool.shutdown();
    }
}
