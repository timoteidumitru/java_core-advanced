package com.javaPlayground.concurrency.threads;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockExample {

    private final Map<String, Double> prices = new HashMap<>();
    private final ReadWriteLock rw = new ReentrantReadWriteLock();

    public double getPrice(String product) {
        rw.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + " reading...");
            Thread.sleep(300); // simulate read work
            return prices.getOrDefault(product, 0.0);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return 0.0;
        } finally {
            System.out.println(Thread.currentThread().getName() + " finished reading");
            rw.readLock().unlock();
        }
    }

    public void updatePrice(String product, double newPrice) {
        rw.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + " writing...");
            Thread.sleep(500); // simulate write work
            prices.put(product, newPrice);
            System.out.println(Thread.currentThread().getName() + " updated price");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            rw.writeLock().unlock();
        }
    }

    public static void main(String[] args) {

        ReadWriteLockExample priceCache = new ReadWriteLockExample();
        priceCache.updatePrice("Laptop", 1500.0); // initial value

        // Reader task
        Runnable reader = () -> {
            for (int i = 0; i < 3; i++) {
                double p = priceCache.getPrice("Laptop");
                System.out.println(Thread.currentThread().getName() +
                        " sees price: " + p);
            }
        };

        // Writer task
        Runnable writer = () -> {
            try {
                Thread.sleep(700); // let readers start first
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            priceCache.updatePrice("Laptop", 2000.0);
        };

        // Start multiple readers
        Thread r1 = new Thread(reader, "Reader-1");
        Thread r2 = new Thread(reader, "Reader-2");
        Thread r3 = new Thread(reader, "Reader-3");

        // Start one writer
        Thread w1 = new Thread(writer, "Writer-1");

        r1.start();
        r2.start();
        r3.start();
        w1.start();
    }
}
