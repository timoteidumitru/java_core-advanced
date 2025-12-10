package com.javaPlayground.concurrency.modernConcurrency;

import java.util.concurrent.*;

public class VirtualThreadExample {
    public static void main(String[] args) throws Exception {
        try (ExecutorService exec = Executors.newVirtualThreadPerTaskExecutor()) {

            for (int i = 0; i < 1_000_000; i++) {
                int id = i;
                exec.submit(() -> {
                    Thread.sleep(10);
                    if (id % 100_000 == 0)
                        System.out.println("Task " + id + " done on " + Thread.currentThread());
                    return null;
                });
            }
        }
        System.out.println("Done!");
    }
}
