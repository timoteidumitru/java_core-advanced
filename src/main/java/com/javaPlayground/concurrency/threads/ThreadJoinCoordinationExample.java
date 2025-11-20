package com.javaPlayground.concurrency.threads;

public class ThreadJoinCoordinationExample {
    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(() -> {
            System.out.println("Worker 1 starting...");
            try { Thread.sleep(1000); } catch (InterruptedException ignored) {
            }
            System.out.println("Worker 1 done.");
        });

        Thread t2 = new Thread(() -> {
            System.out.println("Worker 2 starting...");
            try { Thread.sleep(1500); } catch (InterruptedException ignored) {}
            System.out.println("Worker 2 done.");
        });

        t1.start();
        t2.start();

        t1.join();  // Wait until t1 finishes
        t2.join();  // Wait until t2 finishes

        System.out.println("All workers completed → continuing program.");
    }

}
