package com.javaPlayground.concurrency.latch;

import java.util.concurrent.CountDownLatch;

public class ResourceInitializationExample {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        new Thread(new ConfigLoader(latch)).start();

        // Main thread waits for configuration to be loaded
        latch.await();

        System.out.println("Configuration loaded. Server is starting...");
    }
}

class ConfigLoader implements Runnable {
    private final CountDownLatch latch;

    public ConfigLoader(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {
        System.out.println("Loading configuration...");
        try {
            Thread.sleep(3000); // Simulate loading configuration
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Configuration loaded.");
        latch.countDown(); // Signal that configuration is complete
    }
}

