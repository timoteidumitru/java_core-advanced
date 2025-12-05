package com.javaPlayground.concurrency.concurrentCollections;

import java.util.concurrent.*;

public class LinkedBlockingQueueExample {
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<Integer> queue = new LinkedBlockingQueue<>();

        Runnable producer = () -> {
            try {
                for (int i = 1; i <= 5; i++) {
                    queue.put(i);
                    System.out.println("Produced " + i);
                }
            } catch (InterruptedException ignored) {}
        };

        Runnable consumer = () -> {
            try {
                for (int i = 1; i <= 5; i++) {
                    int val = queue.take();
                    System.out.println("Consumed " + val);
                }
            } catch (InterruptedException ignored) {}
        };

        new Thread(producer).start();
        new Thread(consumer).start();
    }
}

