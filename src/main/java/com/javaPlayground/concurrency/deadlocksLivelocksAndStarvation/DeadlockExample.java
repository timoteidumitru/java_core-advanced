package com.javaPlayground.concurrency.deadlocksLivelocksAndStarvation;

public class DeadlockExample {

    private static final Object LOCK_A = new Object();
    private static final Object LOCK_B = new Object();

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            synchronized (LOCK_A) {
                System.out.println("Thread 1 acquire LOCK 1 and waiting for Thread 2 to complete its job.");
                sleep(500);
                synchronized (LOCK_B) {
                    System.out.println("Thread 1 acquired both locks");
                }
            }
        });

        Thread t2 = new Thread(() -> {
            synchronized (LOCK_B) {
                System.out.println("Thread 2 acquire LOCK 2 and waiting for Thread 1 to complete its job.");
                sleep(500);
                synchronized (LOCK_A) {
                    System.out.println("Thread 2 acquired both locks");
                }
            }
        });

        t1.start();
        t2.start();
    }

    static void sleep(long ms) {
        try { Thread.sleep(ms); } catch (InterruptedException ignored) {}
    }

}
