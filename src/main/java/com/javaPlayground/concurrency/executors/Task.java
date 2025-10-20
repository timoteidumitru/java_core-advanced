package com.javaPlayground.concurrency.executors;

class Task implements Runnable {
    private final String taskName;

    Task(String name) {
        taskName = name;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println(taskName + " is running: " + i);
            try {
                Thread.sleep(1000); // Sleep for 1 second
            } catch (InterruptedException e) {
                System.out.println(taskName + " interrupted.");
            }
        }
        System.out.println(taskName + " exiting.");
    }
}
