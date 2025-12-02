package com.javaPlayground.concurrency.phaserCyclicBarrierAndCountDownLatch;

import java.util.concurrent.*;

public class SyncBenchmark {

    private static final int WORKERS = 8;
    private static final int PHASES = 3;

    public static void main(String[] args) throws Exception {

        System.out.println("===== BENCHMARK START =====");
        System.out.println("Workers = " + WORKERS + ", Phases = " + PHASES);

        System.out.println("\n--- CountDownLatch Benchmark ---");
        benchmarkLatch();

        System.out.println("\n--- CyclicBarrier Benchmark ---");
        benchmarkBarrier();

        System.out.println("\n--- Phaser Benchmark ---");
        benchmarkPhaser();

        System.out.println("\n===== BENCHMARK END =====");
    }

    // ---------------------------------------------------------
    // CountDownLatch benchmark
    // ---------------------------------------------------------
    private static void benchmarkLatch() throws InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(WORKERS);

        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch doneLatch = new CountDownLatch(WORKERS);

        long start = System.currentTimeMillis();

        for (int i = 0; i < WORKERS; i++) {
            pool.submit(() -> {
                try {
                    startLatch.await();
                    doWork(); // just some CPU work
                } catch (InterruptedException ignored) {}
                finally {
                    doneLatch.countDown();
                }
            });
        }

        startLatch.countDown();   // release all workers
        doneLatch.await();        // wait until all finish

        long end = System.currentTimeMillis();

        pool.shutdown();

        System.out.println("CountDownLatch time: " + (end - start) + " ms");
    }

    // ---------------------------------------------------------
    // CyclicBarrier benchmark
    // ---------------------------------------------------------
    private static void benchmarkBarrier() throws InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(WORKERS);

        CyclicBarrier barrier = new CyclicBarrier(WORKERS);
        CountDownLatch done = new CountDownLatch(WORKERS);

        long start = System.currentTimeMillis();

        for (int i = 0; i < WORKERS; i++) {
            pool.submit(() -> {
                try {
                    for (int p = 0; p < PHASES; p++) {
                        doWork();
                        barrier.await();
                    }
                } catch (Exception ignored) {}
                finally {
                    done.countDown();
                }
            });
        }

        done.await();

        long end = System.currentTimeMillis();
        pool.shutdown();

        System.out.println("CyclicBarrier time: " + (end - start) + " ms");
    }

    // ---------------------------------------------------------
    // Phaser benchmark
    // ---------------------------------------------------------
    private static void benchmarkPhaser() throws InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(WORKERS);

        Phaser phaser = new Phaser(WORKERS + 1); // +1 for main

        long start = System.currentTimeMillis();

        for (int i = 0; i < WORKERS; i++) {
            pool.submit(() -> {
                for (int p = 0; p < PHASES; p++) {
                    doWork();
                    phaser.arriveAndAwaitAdvance();
                }
                phaser.arriveAndDeregister();
            });
        }

        // main participates in phases
        for (int p = 0; p < PHASES; p++) {
            phaser.arriveAndAwaitAdvance();
        }

        long end = System.currentTimeMillis();

        pool.shutdown();
        System.out.println("Phaser time: " + (end - start) + " ms");
    }

    // ---------------------------------------------------------
    // CPU work to simulate real scenario
    // ---------------------------------------------------------
    private static void doWork() {
        long x = 0;
        for (int i = 0; i < 200_000; i++) {
            x += (i * 31) % 7;
        }
    }
}
