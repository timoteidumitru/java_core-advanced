package com.javaPlayground.concurrency.realWorldConcurrencyPatterns;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class ProducerConsumerWithBackpressure {

    private static final int QUEUE_CAPACITY = 5;
    private static final int NUM_CONSUMERS = 3;

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<String> queue = new LinkedBlockingQueue<>(QUEUE_CAPACITY);

        ExecutorService producerPool = Executors.newSingleThreadExecutor();
        ExecutorService consumerPool = Executors.newFixedThreadPool(NUM_CONSUMERS);

        // Producer (produces fast)
        producerPool.submit(() -> {
            int counter = 0;
            while (counter < 20) {
                try {
                    String item = "Task-" + counter;
                    queue.put(item); // BACKPRESSURE: blocks if full
                    System.out.println("[Producer] Produced: " + item);
                    counter++;
                    Thread.sleep(100); // fast producer
                } catch (InterruptedException e) {
                    return;
                }
            }
        });

        // Consumers (consume slower)
        for (int i = 0; i < NUM_CONSUMERS; i++) {
            final int id = i;
            consumerPool.submit(() -> {
                try {
                    while (true) {
                        String item = queue.take();
                        System.out.println("   [Consumer " + id + "] Processed: " + item);
                        Thread.sleep(500); // slow consumer
                    }
                } catch (InterruptedException e) {
                    return;
                }
            });
        }

        Thread.sleep(8000);

        producerPool.shutdownNow();
        consumerPool.shutdownNow();
    }

}
