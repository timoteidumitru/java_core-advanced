package com.javaPlayground.concurrency.concurrentCollections;

import java.util.concurrent.SynchronousQueue;

public class SynchronousQueueExample {
    public static void main(String[] args) {
        SynchronousQueue<String> queue = new SynchronousQueue<>();

        Thread producer = new Thread(() -> {
            try {
                System.out.println("Producer waiting to hand off...");
                queue.put("DATA");
                System.out.println("Producer handed off!");
            } catch (InterruptedException ignored) {}
        });

        Thread consumer = new Thread(() -> {
            try {
                Thread.sleep(1000);
                System.out.println("Consumer waiting...");
                String data = queue.take();
                System.out.println("Consumer received: " + data);
            } catch (InterruptedException ignored) {}
        });

        producer.start();
        consumer.start();
    }
}

