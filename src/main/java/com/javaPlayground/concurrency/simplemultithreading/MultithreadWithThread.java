package com.javaPlayground.concurrency.simplemultithreading;

public class MultithreadWithThread extends Thread {
    private final String threadName;

    MultithreadWithThread(String name) {
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
