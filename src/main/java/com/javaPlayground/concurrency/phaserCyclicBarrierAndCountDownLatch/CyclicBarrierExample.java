package com.javaPlayground.concurrency.phaserCyclicBarrierAndCountDownLatch;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CyclicBarrierExample {
    public static void main(String[] args) {
        int workers = 5;

        CyclicBarrier barrier = new CyclicBarrier(workers,
                () -> System.out.println("\n== All workers completed this step ==\n")
        );

        ExecutorService pool = Executors.newFixedThreadPool(workers);

        for (int i = 1; i <= workers; i++) {
            int id = i;
            pool.submit(() -> performWork(id, barrier));
        }

        pool.shutdown();
    }

    static void performWork(int id, CyclicBarrier barrier) {
        try {
            for (int step = 1; step <= 3; step++) {
                System.out.println("Worker " + id + " doing step " + step);
                Thread.sleep(300 + id * 100);
                barrier.await(); // WAIT for other workers
            }
        } catch (Exception ignored) {}
    }
}
