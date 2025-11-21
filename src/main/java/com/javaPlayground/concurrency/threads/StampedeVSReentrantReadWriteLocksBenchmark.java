package com.javaPlayground.concurrency.threads;

import java.util.concurrent.*;
import java.util.concurrent.locks.*;

public class StampedeVSReentrantReadWriteLocksBenchmark {

    // ============================================================
    // Variant 1: ReentrantReadWriteLock
    // ============================================================
    static class RWLockPoint {
        private double x = 0, y = 0;
        private final ReadWriteLock lock = new ReentrantReadWriteLock();

        public void writeMove(double dx, double dy) {
            lock.writeLock().lock();
            try {
                x += dx; y += dy;
            } finally {
                lock.writeLock().unlock();
            }
        }

        public double readDistance() {
            lock.readLock().lock();
            try {
                return Math.sqrt(x * x + y * y);
            } finally {
                lock.readLock().unlock();
            }
        }
    }

    // ============================================================
    // Variant 2: StampedLock with optimistic read
    // ============================================================
    static class StampedLockPoint {
        private double x = 0, y = 0;
        private final StampedLock lock = new StampedLock();

        public void writeMove(double dx, double dy) {
            long stamp = lock.writeLock();
            try {
                x += dx; y += dy;
            } finally {
                lock.unlockWrite(stamp);
            }
        }

        public double readDistance() {
            long stamp = lock.tryOptimisticRead();
            double cx = x, cy = y;

            if (!lock.validate(stamp)) {
                stamp = lock.readLock();
                try {
                    cx = x; cy = y;
                } finally {
                    lock.unlockRead(stamp);
                }
            }

            return Math.sqrt(cx * cx + cy * cy);
        }
    }

    // ============================================================
    // BENCHMARK METHOD
    // ============================================================
    public static long runBenchmark(Runnable task) throws InterruptedException {
        ExecutorService exec = Executors.newFixedThreadPool(8);

        long start = System.nanoTime();

        for (int i = 0; i < 8; i++) {
            exec.submit(task);
        }

        exec.shutdown();
        exec.awaitTermination(5, TimeUnit.SECONDS);

        return System.nanoTime() - start;
    }


    public static void main(String[] args) throws Exception {

        RWLockPoint rwPoint = new RWLockPoint();
        StampedLockPoint stPoint = new StampedLockPoint();

        // Same workload for both tests:
        Runnable rwTask = () -> {
            for (int i = 0; i < 500_000; i++) {
                if (i % 1000 == 0) rwPoint.writeMove(1, 1);
                else rwPoint.readDistance();
            }
        };

        Runnable stTask = () -> {
            for (int i = 0; i < 500_000; i++) {
                if (i % 1000 == 0) stPoint.writeMove(1, 1);
                else stPoint.readDistance();
            }
        };

        long rwTime = runBenchmark(rwTask);
        long stTime = runBenchmark(stTask);

        System.out.println("=========================================");
        System.out.println("    LOCK PERFORMANCE BENCHMARK");
        System.out.println("=========================================");
        System.out.println("ReentrantReadWriteLock time: " + rwTime / 1_000_000 + " ms");
        System.out.println("StampedLock time:            " + stTime / 1_000_000 + " ms");
    }
}
