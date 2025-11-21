package com.javaPlayground.concurrency.threads;

import java.util.concurrent.locks.StampedLock;

public class StampedLockExample {

    private double x, y; // shared state
    private final StampedLock lock = new StampedLock();

    // ---- WRITER: updates the point ----
    public void move(double dx, double dy) {
        long stamp = lock.writeLock();
        try {
            x += dx;
            y += dy;
            System.out.println(Thread.currentThread().getName()
                    + " wrote new values: x=" + x + ", y=" + y);
        } finally {
            lock.unlockWrite(stamp);
        }
    }

    // ---- READER: optimistic read first ----
    public double distanceFromOrigin() {
        long stamp = lock.tryOptimisticRead();
        double currentX = x;  // read without locking
        double currentY = y;

        // Validate optimistic read
        if (!lock.validate(stamp)) {
            // Fallback → acquire full read lock
            stamp = lock.readLock();
            try {
                currentX = x;
                currentY = y;
            } finally {
                lock.unlockRead(stamp);
            }
        }

        return Math.sqrt(currentX * currentX + currentY * currentY);
    }


    public static void main(String[] args) throws InterruptedException {

        StampedLockExample point = new StampedLockExample();

        // READER THREADS (many readers)
        Runnable reader = () -> {
            for (int i = 0; i < 5; i++) {
                double dist = point.distanceFromOrigin();
                System.out.println(Thread.currentThread().getName()
                        + " read distance = " + dist);

                try {
                    Thread.sleep(200);
                } catch (InterruptedException ignored) {}
            }
        };

        // WRITER THREAD (only 1 writer)
        Runnable writer = () -> {
            try {
                for (int i = 0; i < 3; i++) {
                    point.move(1, 1);  // modify the state
                    Thread.sleep(500);
                }
            } catch (InterruptedException ignored) {}
        };

        Thread t1 = new Thread(reader, "Reader-1");
        Thread t2 = new Thread(reader, "Reader-2");
        Thread t3 = new Thread(writer, "Writer");

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();

        System.out.println("\nAcquiring metrics complete.");
    }
}
