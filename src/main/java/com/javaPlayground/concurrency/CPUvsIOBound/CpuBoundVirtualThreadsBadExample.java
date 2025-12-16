package com.javaPlayground.concurrency.CPUvsIOBound;

import java.util.concurrent.*;

public class CpuBoundVirtualThreadsBadExample {

    static final int TASKS = 20_000;

    public static void main(String[] args) throws Exception {

        try (ExecutorService exec =
                     Executors.newVirtualThreadPerTaskExecutor()) {

            long start = System.nanoTime();

            for (int i = 0; i < TASKS; i++) {
                exec.submit(CpuBoundVirtualThreadsBadExample::heavyComputation);
            }

            exec.shutdown();
            exec.awaitTermination(1, TimeUnit.MINUTES);

            long end = System.nanoTime();

            System.out.println("CPU-bound (Virtual Threads ❌)");
            System.out.println("Time: " + (end - start) / 1_000_000 + " ms");
        }
    }

    static void heavyComputation() {
        double result = 0;
        for (int i = 0; i < 1_000_000; i++) {
            result += Math.sqrt(i);
        }
    }
}

