package com.javaPlayground.concurrency.latch;

import java.util.concurrent.CountDownLatch;

public class DataProcessingExample {
    public static void main(String[] args) throws InterruptedException {
        int numberOfTasks = 4;
        CountDownLatch latch = new CountDownLatch(numberOfTasks);

        for (int i = 1; i <= numberOfTasks; i++) {
            new Thread(new DataProcessor(i, latch)).start();
        }

        // Main thread waits for all tasks to complete
        latch.await();

        System.out.println("All data processed. Generating report...");
    }
}

class DataProcessor implements Runnable {
    private final int taskId;
    private final CountDownLatch latch;

    public DataProcessor(int taskId, CountDownLatch latch) {
        this.taskId = taskId;
        this.latch = latch;
    }

    @Override
    public void run() {
        System.out.println("Processing task " + taskId + "...");
        try {
            Thread.sleep(1000); // Simulate task processing
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Task " + taskId + " completed.");
        latch.countDown(); // Decrement latch count
    }
}

