package com.javaPlayground.concurrency.realWorldConcurrencyPatterns;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class VirtualThreadProducerConsumerExample {

    private static final int QUEUE_CAPACITY = 5;

    public static void main(String[] args) throws InterruptedException {

        BlockingQueue<String> queue = new LinkedBlockingQueue<>(QUEUE_CAPACITY);

        ExecutorService producer = Executors.newVirtualThreadPerTaskExecutor();
        ExecutorService consumers = Executors.newVirtualThreadPerTaskExecutor();

        // Producer - very fast
        producer.submit(() -> {
            for (int i = 0; i <= 50; i++) {
                try {
                    queue.put("Task-" + i); // provides natural backpressure
                    System.out.println("[Producer] Produced Task-" + i);
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    return;
                }
            }
        });

        // Consumers - spin up 10 virtual threads
        for (int i = 0; i < 25; i++) {
            int id = i;
            consumers.submit(() -> {
                try {
                    while (true) {
                        String task = queue.take();
                        System.out.println("   [VT Consumer " + id + "] Processing " + task);
                        Thread.sleep(300);
                    }
                } catch (InterruptedException e) {
                    return e.getMessage();
                }
            });
        }

        Thread.sleep(6000);
        producer.shutdownNow();
        consumers.shutdownNow();
    }

}
