package com.javaPlayground.concurrency.latch;

import java.util.concurrent.CountDownLatch;

public class RaceExample {
    public static void main(String[] args) throws InterruptedException {
        int numberOfRunners = 3;
        CountDownLatch readyLatch = new CountDownLatch(numberOfRunners);
        CountDownLatch startLatch = new CountDownLatch(1);

        for (int i = 1; i <= numberOfRunners; i++) {
            new Thread(new Runner(i, readyLatch, startLatch)).start();
        }

        // Wait for all runners to be ready
        readyLatch.await();

        System.out.println("All runners are ready. Starting the race!");
        startLatch.countDown(); // Signal race start
    }
}

class Runner implements Runnable {
    private final int runnerId;
    private final CountDownLatch readyLatch;
    private final CountDownLatch startLatch;

    public Runner(int runnerId, CountDownLatch readyLatch, CountDownLatch startLatch) {
        this.runnerId = runnerId;
        this.readyLatch = readyLatch;
        this.startLatch = startLatch;
    }

    @Override
    public void run() {
        System.out.println("Runner " + runnerId + " is getting ready...");
        readyLatch.countDown(); // Signal that runner is ready

        try {
            startLatch.await(); // Wait for race to start
            System.out.println("Runner " + runnerId + " is running!");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

