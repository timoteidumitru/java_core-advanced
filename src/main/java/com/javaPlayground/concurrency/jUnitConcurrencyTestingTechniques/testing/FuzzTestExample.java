package com.javaPlayground.concurrency.jUnitConcurrencyTestingTechniques.testing;

import java.util.Random;

public class FuzzTestExample {

    static volatile boolean running = true;

    public static void main(String[] args) throws Exception {
        Random random = new Random();

        Thread worker = new Thread(() -> {
            while (running) {
                try {
                    Thread.sleep(random.nextInt(5));
                } catch (InterruptedException e) {
                    System.out.println("Interrupted!");
                }
            }
        });

        worker.start();

        for (int i = 0; i < 1000; i++) {
            Thread.sleep(random.nextInt(3));
            worker.interrupt();
        }

        running = false;
        worker.join();

        System.out.println("Fuzz test completed");
    }
}

