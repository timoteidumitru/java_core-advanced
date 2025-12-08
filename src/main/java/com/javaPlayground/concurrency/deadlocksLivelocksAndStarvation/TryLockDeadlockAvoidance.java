package com.javaPlayground.concurrency.deadlocksLivelocksAndStarvation;

import java.util.concurrent.locks.ReentrantLock;

public class TryLockDeadlockAvoidance {

    private final ReentrantLock lockA = new ReentrantLock();
    private final ReentrantLock lockB = new ReentrantLock();

    public void worker1() {
        acquireLocks(lockA, lockB);
        System.out.println("Worker1: acquired locks");
        lockA.unlock();
        lockB.unlock();
    }

    public void worker2() {
        acquireLocks(lockB, lockA);
        System.out.println("Worker2: acquired locks");
        lockB.unlock();
        lockA.unlock();
    }

    private void acquireLocks(ReentrantLock first, ReentrantLock second) {
        while (true) {
            boolean gotFirst = false;
            boolean gotSecond = false;

            try {
                gotFirst = first.tryLock();
                gotSecond = second.tryLock();

                if (gotFirst && gotSecond) {
                    return;
                }
            } finally {
                if (gotFirst && !gotSecond) {
                    first.unlock();
                }

                if (gotSecond && !gotFirst) {
                    second.unlock();
                }
            }

            try {
                Thread.sleep(5);
            } catch (InterruptedException ignored) {}
        }
    }

    public static void main(String[] args) {
        TryLockDeadlockAvoidance demo = new TryLockDeadlockAvoidance();

        new Thread(demo::worker1, "Thread-1").start();
        new Thread(demo::worker2, "Thread-2").start();
    }
}


