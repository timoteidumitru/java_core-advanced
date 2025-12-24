package com.javaPlayground.concurrency.testingAndDebuggingTechniques.debugging;

import java.util.concurrent.locks.ReentrantLock;

public class LoomGoodPinning {

    static final ReentrantLock lock = new ReentrantLock();

    static void blockingCall() {
        lock.lock();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100_000; i++) {
            Thread.startVirtualThread(LoomGoodPinning::blockingCall);
        }
    }
}

