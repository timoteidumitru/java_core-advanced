package com.javaPlayground.concurrency.lockFreeCASAndAtomicClasses;

import java.util.concurrent.atomic.AtomicInteger;

public class CASLockFreeInventory {

    private final AtomicInteger stock = new AtomicInteger(100);

    public boolean buy() {
        int current;
        do {
            current = stock.get();
            if (current == 0) return false;
        } while (!stock.compareAndSet(current, current - 1));
        return true;
    }

    public int getStock() {
        return stock.get();
    }

    public static void main(String[] args) throws InterruptedException {

        CASLockFreeInventory inventory = new CASLockFreeInventory();

        Runnable buyer = () -> {
            for (int i = 0; i < 60; i++) {
                if (inventory.buy()) {
                    // bought successfully
                }
            }
        };

        Thread t1 = new Thread(buyer);
        Thread t2 = new Thread(buyer);
        Thread t3 = new Thread(buyer);

        t1.start(); t2.start(); t3.start();
        t1.join(); t2.join(); t3.join();

        System.out.println("Stock left = " + inventory.getStock());
    }
}
