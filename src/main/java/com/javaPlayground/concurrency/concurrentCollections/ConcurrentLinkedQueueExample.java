package com.javaPlayground.concurrency.concurrentCollections;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ConcurrentLinkedQueueExample {

    public static void main(String[] args) {
        Queue<String> queue = new ConcurrentLinkedQueue<>();

        // Producers
        Thread p1 = new Thread(() -> queue.add("Task-A"));
        Thread p2 = new Thread(() -> queue.add("Task-B"));

        // Consumer
        Thread consumer = new Thread(() -> {
            try { Thread.sleep(100); } catch (Exception ignored) {}
            String task;
            while ((task = queue.poll()) != null) {
                System.out.println("Processing " + task);
            }
        });

        p1.start();
        p2.start();
        consumer.start();
    }
}
