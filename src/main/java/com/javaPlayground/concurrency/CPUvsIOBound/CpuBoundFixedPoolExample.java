package com.javaPlayground.concurrency.CPUvsIOBound;

import java.util.concurrent.*;

public class CpuBoundFixedPoolExample {

    static final int TASKS = 20_000;

    public static void main(String[] args) throws Exception {

        int cores = Runtime.getRuntime().availableProcessors();
        ExecutorService pool = Executors.newFixedThreadPool(cores);

        long start = System.nanoTime();

        for (int i = 0; i < TASKS; i++) {
            pool.submit(CpuBoundFixedPoolExample::heavyComputation);
        }

        pool.shutdown();
        pool.awaitTermination(1, TimeUnit.MINUTES);

        long end = System.nanoTime();

        System.out.println("CPU-bound (Fixed Pool)");
        System.out.println("Cores: " + cores);
        System.out.println("Time : " + (end - start) / 1_000_000 + " ms");
    }

    static void heavyComputation() {
        double result = 0;
        for (int i = 0; i < 1_000_000; i++) {
            result += Math.sqrt(i);
        }
    }
}

