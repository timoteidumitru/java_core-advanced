package com.javaPlayground.concurrency.executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class Runner {
    public static void main(String[] args) {
        // Creating a fixed thread pool with 2 threads
        ExecutorService executor = Executors.newFixedThreadPool(2);

        // Submitting tasks to the executor
        executor.submit(new Task("Job - 1"));
        executor.submit(new Task("Job - 2"));
        executor.submit(new Task("Job - 3"));


        executor.shutdown();

        try {
            // Wait for existing tasks to terminate
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                // Forcefully shutdown if not terminated within the time
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }

        System.out.println("Main thread exiting.");
    }
}
