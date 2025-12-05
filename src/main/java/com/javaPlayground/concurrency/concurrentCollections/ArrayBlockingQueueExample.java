package com.javaPlayground.concurrency.concurrentCollections;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ArrayBlockingQueueExample {
    public static void main(String[] args) {
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(2); // fixed capacity

        new Thread(() -> {
            try {
                for (int i = 1; i <= 4; i++) {
                    queue.put(i);
                    System.out.println("Produced = " + i);
                }
            } catch (InterruptedException ignored) {}
        }).start();

        new Thread(() -> {
            try {
                Thread.sleep(1000);
                while (true) {
                    Integer item = queue.take();
                    System.out.println("Consumed = " + item);
                }
            } catch (Exception ignored) {}
        }).start();
    }
}

