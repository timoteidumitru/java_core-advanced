package com.javaPlayground.concurrency.deadlocksLivelocksAndStarvation;

import java.util.concurrent.locks.ReentrantLock;

public class StarvationExample {

    private static final ReentrantLock lock = new ReentrantLock(true); // unfair lock

    public static void main(String[] args) {

        // Fast threads
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                while (true) {
                    if (lock.tryLock()) {
                        try {
                            System.out.println("Fast thread got the lock!");
                            Thread.sleep(10);
                        } catch (Exception ignored) {
                        } finally {
                            lock.unlock();
                        }
                    }
                }
            }).start();
        }

        // Slow thread
        new Thread(() -> {
            while (true) {
                lock.lock();
                try {
                    System.out.println("Slow thread got the lock!");
                    Thread.sleep(200); }
                catch (Exception ignored) {}
                finally { lock.unlock(); }
            }
        }).start();
    }
}

