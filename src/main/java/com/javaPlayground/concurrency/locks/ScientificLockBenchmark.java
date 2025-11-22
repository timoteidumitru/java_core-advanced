package com.javaPlayground.concurrency.locks;

import java.util.concurrent.*;
import java.util.concurrent.locks.*;

public class ScientificLockBenchmark {

    // ================================================================
    // CONFIGURATION
    // ================================================================
    private static final int THREADS = Runtime.getRuntime().availableProcessors();
    private static final int READ_THREADS = THREADS - 1;
    private static final int WRITE_THREADS = 1;
    private static final int OPS_PER_THREAD = 2_000_000;
    private static final int RUNS = 5;

    // ================================================================
    // FALSE SHARING PROTECTED COUNTERS
    // (each in separate cache lines)
    // ================================================================
    public static class PaddedDouble {
        public volatile double value = 0;
        // padding to avoid false sharing (fills cache line)
        public long p1, p2, p3, p4, p5, p6, p7;
    }

    // ================================================================
    // SHARED STATE — RWLock & StampedLock
    // ================================================================
    static class RWState {
        final ReadWriteLock lock = new ReentrantReadWriteLock();
        final PaddedDouble x = new PaddedDouble();
        final PaddedDouble y = new PaddedDouble();
    }

    static class StampedState {
        final StampedLock lock = new StampedLock();
        final PaddedDouble x = new PaddedDouble();
        final PaddedDouble y = new PaddedDouble();
    }

    // ================================================================
    // THREAD AFFINITY (CPU PINNING)
    // ================================================================
    static void pinToCpu(int threadIndex) {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            // Windows affinity using JNA or Process API (simplified: no-op here)
            return;
        }

        try {
            long mask = 1L << (threadIndex % THREADS);
            String pid = String.valueOf(ProcessHandle.current().pid());
            new ProcessBuilder("taskset", "-p", Long.toHexString(mask), pid).start().waitFor();
        } catch (Exception ignore) {}
    }

    // ================================================================
    // RWLock Bench Tasks
    // ================================================================
    static Runnable rwReadTask(RWState s) {
        return () -> {
            pinToCpu((int) Thread.currentThread().getId());
            for (int i = 0; i < OPS_PER_THREAD; i++) {
                s.lock.readLock().lock();
                double d;
                try {
                    d = Math.sqrt(s.x.value * s.x.value + s.y.value * s.y.value);
                } finally {
                    s.lock.readLock().unlock();
                }
                if (d == -1) System.out.print(""); // prevent dead-code elimination
            }
        };
    }

    static Runnable rwWriteTask(RWState s) {
        return () -> {
            pinToCpu((int) Thread.currentThread().getId());
            for (int i = 0; i < OPS_PER_THREAD; i++) {
                s.lock.writeLock().lock();
                try {
                    s.x.value++;
                    s.y.value++;
                } finally {
                    s.lock.writeLock().unlock();
                }
            }
        };
    }

    // ================================================================
    // StampedLock Bench Tasks
    // ================================================================
    static Runnable stampedReadTask(StampedState s) {
        return () -> {
            pinToCpu((int) Thread.currentThread().getId());
            for (int i = 0; i < OPS_PER_THREAD; i++) {
                long stamp = s.lock.tryOptimisticRead();
                double cx = s.x.value, cy = s.y.value;

                if (!s.lock.validate(stamp)) {
                    stamp = s.lock.readLock();
                    try {
                        cx = s.x.value;
                        cy = s.y.value;
                    } finally {
                        s.lock.unlockRead(stamp);
                    }
                }

                double d = Math.sqrt(cx * cx + cy * cy);
                if (d == -1) System.out.print("");
            }
        };
    }

    static Runnable stampedWriteTask(StampedState s) {
        return () -> {
            pinToCpu((int) Thread.currentThread().getId());
            for (int i = 0; i < OPS_PER_THREAD; i++) {
                long stamp = s.lock.writeLock();
                try {
                    s.x.value++;
                    s.y.value++;
                } finally {
                    s.lock.unlockWrite(stamp);
                }
            }
        };
    }

    // ================================================================
    // BENCHMARK RUNNER
    // ================================================================
    private static long runBenchmark(Runnable readTask, Runnable writeTask) throws InterruptedException {

        ExecutorService exec = Executors.newFixedThreadPool(THREADS);

        long start = System.nanoTime();

        // submit writer
        exec.submit(writeTask);

        // submit readers
        for (int i = 0; i < READ_THREADS; i++) {
            exec.submit(readTask);
        }

        exec.shutdown();
        exec.awaitTermination(30, TimeUnit.SECONDS);

        return System.nanoTime() - start;
    }

    // ================================================================
    // MAIN
    // ================================================================
    public static void main(String[] args) throws Exception {

        System.out.println("\n=============================");
        System.out.println(" SCIENTIFIC LOCK BENCHMARK");
        System.out.println("=============================\n");
        System.out.println("Threads: " + THREADS);
        System.out.println("Read/Write ratio: " + READ_THREADS + " readers / " + WRITE_THREADS + " writer");
        System.out.println("Ops/thread: " + OPS_PER_THREAD);
        System.out.println();

        long[] rwTimes = new long[RUNS];
        long[] stTimes = new long[RUNS];

        // ---- RUN RWLock ----
        for (int i = 0; i < RUNS; i++) {
            RWState s = new RWState();
            rwTimes[i] = runBenchmark(rwReadTask(s), rwWriteTask(s));
            System.out.println("RWLock Run " + (i + 1) + ": " + (rwTimes[i] / 1_000_000) + " ms");
        }

        System.out.println();

        // ---- RUN StampedLock ----
        for (int i = 0; i < RUNS; i++) {
            StampedState s = new StampedState();
            stTimes[i] = runBenchmark(stampedReadTask(s), stampedWriteTask(s));
            System.out.println("StampedLock Run " + (i + 1) + ": " + (stTimes[i] / 1_000_000) + " ms");
        }

        // ---- Summary ----
        System.out.println("\n--- SUMMARY ---");

        long rwAvg = avg(rwTimes);
        long stAvg = avg(stTimes);

        System.out.println("RWLock  avg: " + (rwAvg / 1_000_000) + " ms");
        System.out.println("Stamped avg: " + (stAvg / 1_000_000) + " ms");
        System.out.println("\nStampedLock speedup: " + String.format("%.2f×", (double) rwAvg / stAvg));
    }

    private static long avg(long[] arr) {
        long sum = 0;
        for (long v : arr) sum += v;
        return sum / arr.length;
    }
}
