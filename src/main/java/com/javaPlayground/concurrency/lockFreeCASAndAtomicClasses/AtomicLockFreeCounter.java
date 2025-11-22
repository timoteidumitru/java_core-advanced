package com.javaPlayground.concurrency.lockFreeCASAndAtomicClasses;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicLockFreeCounter {

    private final AtomicInteger counter = new AtomicInteger(0);

    public void increment() {
        counter.incrementAndGet();
    }

    public int get() {
        return counter.get();
    }

    public static void main(String[] args) throws InterruptedException {

        AtomicLockFreeCounter ex = new AtomicLockFreeCounter();

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 100_000_000; i++) ex.increment();
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 100_000_000; i++) ex.increment();
        });

        t1.start(); t2.start();
        t1.join(); t2.join();

        System.out.println("Final count = " + ex.get());
    }

}
