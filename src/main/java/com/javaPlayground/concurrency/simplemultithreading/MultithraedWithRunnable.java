package com.javaPlayground.concurrency.simplemultithreading;

public class MultithraedWithRunnable implements Runnable {
    private final String threadName;

    MultithraedWithRunnable(String name) {
        threadName = name;
    }

    @Override
    public void run() {
        for(int i = 0; i < 5; i++) {
            System.out.println(threadName + " is running: " + i);
            try {
                Thread.sleep(1000); // Sleep for 1 second
            } catch (InterruptedException e) {
                System.out.println(threadName + " interrupted.");
            }
        }
        System.out.println(threadName + " exiting.");
    }
}

