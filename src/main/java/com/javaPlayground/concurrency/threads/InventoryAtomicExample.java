package com.javaPlayground.concurrency.threads;

import java.util.concurrent.atomic.AtomicInteger;

public class InventoryAtomicExample {

    private final AtomicInteger stock = new AtomicInteger(100);

    public boolean buy() {
        int curr;
        do {
            curr = stock.get();
            if (curr == 0) return false;
        } while (!stock.compareAndSet(curr, curr - 1));
        return true;
    }

    public AtomicInteger getStock() {
        return stock;
    }

    public static void main(String[] args) {

        InventoryAtomicExample inventory = new InventoryAtomicExample();

        // Create a task for each buyer thread
        Runnable buyerTask = () -> {
            String threadName = Thread.currentThread().getName();
            int purchases = 0;

            while (inventory.buy()) {
                purchases++;
                System.out.println(threadName + " successfully bought an item.");
                try { Thread.sleep(100); } catch (InterruptedException e) {}
            }

            System.out.println(threadName + " STOPPED. Items bought: " + purchases);
        };

        // Create multiple threads simulating customers
        Thread t1 = new Thread(buyerTask, "Buyer-1");
        Thread t2 = new Thread(buyerTask, "Buyer-2");
        Thread t3 = new Thread(buyerTask, "Buyer-3");

        // Start all buyers (competition begins!)
        t1.start();
        t2.start();
        t3.start();

        // Wait for them to finish
        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\nFINAL STOCK = " + inventory.getStock().get());
    }
}
