package com.javaPlayground.concurrency.semaphoreAndResourceControl;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class ProducerConsumerExample {

    private static final Queue<Integer> queue = new LinkedList<>();
    private static final int capacity = 10;

    private static final Semaphore empty = new Semaphore(capacity);
    private static final Semaphore full = new Semaphore(0);
    private static final Semaphore mutex = new Semaphore(1);

    static class Producer extends Thread {
        @Override
        public void run() {
            int item = 0;
            try {
                while (true) {
                    empty.acquire();
                    mutex.acquire();

                    queue.add(item);
                    System.out.println("Produced: " + item++);

                    mutex.release();
                    full.release();

                    Thread.sleep(300);
                }
            } catch (Exception ignored) {}
        }
    }

    static class Consumer extends Thread {
        @Override
        public void run() {
            try {
                while (true) {
                    full.acquire();
                    mutex.acquire();

                    int item = queue.remove();
                    System.out.println("Consumed: " + item);

                    mutex.release();
                    empty.release();

                    Thread.sleep(500);
                }
            } catch (Exception ignored) {}
        }
    }

    public static void main(String[] args) {

        new Producer().start();
        new Consumer().start();

    }
}

