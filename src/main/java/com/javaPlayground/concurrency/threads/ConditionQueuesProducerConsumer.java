package com.javaPlayground.concurrency.threads;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionQueuesProducerConsumer {

    private final ReentrantLock lock = new ReentrantLock();
    private final Condition notEmpty = lock.newCondition();
    private final Condition notFull = lock.newCondition();

    private final Object[] items = new Object[10];
    private int putptr, takeptr, count;

    public void put(Object x) throws InterruptedException {
        lock.lock();
        try {
            while (count == items.length)
                notFull.await();   // wait until there's space

            items[putptr] = x;
            if (++putptr == items.length) putptr = 0;
            count++;

            notEmpty.signal();      // signal consumer
        } finally {
            lock.unlock();
        }
    }

    public Object take() throws InterruptedException {
        lock.lock();
        try {
            while (count == 0)
                notEmpty.await();   // wait until something is available

            Object x = items[takeptr];
            if (++takeptr == items.length) takeptr = 0;
            count--;

            notFull.signal();       // signal producer
            return x;
        } finally {
            lock.unlock();
        }
    }


    public static void main(String[] args) {
        ConditionQueuesProducerConsumer queue = new ConditionQueuesProducerConsumer();

        // PRODUCER (adds items)
        Runnable producer = () -> {
            int item = 0;
            try {
                while (true) {
                    queue.put("Item " + item);
                    System.out.println(Thread.currentThread().getName() + " produced: Item " + item);
                    item++;

                    Thread.sleep(1500); // slow down a bit
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };

        // CONSUMER (takes items)
        Runnable consumer = () -> {
            try {
                while (true) {
                    Object val = queue.take();
                    System.out.println(Thread.currentThread().getName() + " consumed: " + val);

                    Thread.sleep(2500); // slower consumer to force backpressure
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };

        // Start 3 producers and 5 consumers
        for (int i = 0; i < 3; i++) {
            new Thread(producer, "Producer-" + i).start();
        }
        for (int i = 0; i < 5; i++) {
            new Thread(consumer, "Consumer-" + i).start();
        }
    }
}
