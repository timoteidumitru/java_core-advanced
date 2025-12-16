package com.javaPlayground.concurrency.CPUvsIoBound;

import java.util.concurrent.*;

public class MixedWorkloadHybridExample {

    static final int TASKS = 5_000;

    public static void main(String[] args) throws Exception {

        int cores = Runtime.getRuntime().availableProcessors();

        ExecutorService cpuPool =
                Executors.newFixedThreadPool(cores);

        try (ExecutorService ioExec =
                     Executors.newVirtualThreadPerTaskExecutor()) {

            long start = System.nanoTime();

            for (int i = 0; i < TASKS; i++) {
                ioExec.submit(() -> {
                    blockingIo();                                           // read
                    cpuPool.submit(MixedWorkloadHybridExample::compute);    // compute
                    blockingIo();                                           // write
                });
            }

            ioExec.shutdown();
            ioExec.awaitTermination(1, TimeUnit.MINUTES);

            cpuPool.shutdown();
            cpuPool.awaitTermination(1, TimeUnit.MINUTES);

            long end = System.nanoTime();

            System.out.println("Mixed workload (Hybrid ✅)");
            System.out.println("Time: " + (end - start) / 1_000_000 + " ms");
        }
    }

    static void blockingIo() {
        try { Thread.sleep(5); } catch (InterruptedException ignored) {}
    }

    static void compute() {
        double r = 0;
        for (int i = 0; i < 500_000; i++) r += Math.log(i + 1);
    }
}

