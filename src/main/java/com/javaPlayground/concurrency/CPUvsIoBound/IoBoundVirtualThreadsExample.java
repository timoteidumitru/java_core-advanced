package com.javaPlayground.concurrency.CPUvsIoBound;

import java.util.concurrent.*;

public class IoBoundVirtualThreadsExample {

    static final int TASKS = 100_000;

    public static void main(String[] args) throws Exception {

        try (ExecutorService exec =
                     Executors.newVirtualThreadPerTaskExecutor()) {

            long start = System.nanoTime();

            for (int i = 0; i < TASKS; i++) {
                exec.submit(IoBoundVirtualThreadsExample::blockingIo);
            }

            exec.shutdown();
            exec.awaitTermination(1, TimeUnit.MINUTES);

            long end = System.nanoTime();

            System.out.println("I/O-bound (Virtual Threads ✅)");
            System.out.println("Tasks: " + TASKS);
            System.out.println("Time : " + (end - start) / 1_000_000 + " ms");
        }
    }

    static void blockingIo() {
        try {
            Thread.sleep(5); // simulate I/O wait
        } catch (InterruptedException ignored) {}
    }
}

