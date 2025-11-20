package com.javaPlayground.concurrency.threads;

public class ThreadInterruptExample {

    public static void main(String[] args) throws InterruptedException {

        Thread worker = new Thread(() -> {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    System.out.println("Working...");
                    Thread.sleep(500); // interruptible blocking
                }
            } catch (InterruptedException e) {
                System.out.println("Interrupted during sleep!");
                Thread.currentThread().interrupt(); // restore flag
            }

            System.out.println("Gracefully stopping thread.");
        });

        worker.start();

        Thread.sleep(1500);
        System.out.println("Requesting stop...");
        worker.interrupt();
    }
}

