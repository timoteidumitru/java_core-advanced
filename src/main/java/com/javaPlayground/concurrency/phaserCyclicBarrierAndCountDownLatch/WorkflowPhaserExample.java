package com.javaPlayground.concurrency.phaserCyclicBarrierAndCountDownLatch;

import java.util.concurrent.Phaser;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WorkflowPhaserExample {

    public static void main(String[] args) {
        int workers = 4;
        Phaser phaser = new Phaser(workers);

        ExecutorService pool = Executors.newFixedThreadPool(workers);

        for (int i = 1; i <= workers; i++) {
            int id = i;
            pool.submit(() -> runWorkflow(id, phaser));
        }

        pool.shutdown();
    }

    static void runWorkflow(int id, Phaser phaser) {
        try {
            load(id);
            phaser.arriveAndAwaitAdvance();   // Phase 0

            process(id);
            phaser.arriveAndAwaitAdvance();   // Phase 1

            save(id);
            phaser.arriveAndAwaitAdvance();   // Phase 2
        } finally {
            phaser.arriveAndDeregister();
        }
    }

    static void load(int id)   { System.out.println("Worker " + id + " loading"); sleep(200); }
    static void process(int id){ System.out.println("Worker " + id + " processing"); sleep(300); }
    static void save(int id)   { System.out.println("Worker " + id + " saving"); sleep(100); }

    static void sleep(long ms) { try { Thread.sleep(ms); } catch (InterruptedException ignored) {} }
}

