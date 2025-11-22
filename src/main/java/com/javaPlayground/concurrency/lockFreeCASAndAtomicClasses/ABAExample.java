package com.javaPlayground.concurrency.lockFreeCASAndAtomicClasses;

import java.util.concurrent.atomic.AtomicStampedReference;

public class ABAExample {

    private final AtomicStampedReference<Integer> ref =
            new AtomicStampedReference<>(100, 0);

    public boolean updateBalance(int newValue) {
        int[] stampHolder = new int[1];
        int currentValue = ref.get(stampHolder);
        int currentStamp = stampHolder[0];

        return ref.compareAndSet(
                currentValue, newValue,
                currentStamp, currentStamp + 1
        );
    }

    public static void main(String[] args) throws InterruptedException {
        ABAExample example = new ABAExample();

        Runnable updater = () -> {
            for (int i = 0; i < 5; i++) {
                boolean success = example.updateBalance(100 + i);
                System.out.println(Thread.currentThread().getName()
                        + " update result: " + success);
            }
        };

        Thread t1 = new Thread(updater, "T1");
        Thread t2 = new Thread(updater, "T2");

        t1.start(); t2.start();
        t1.join(); t2.join();
    }
}
