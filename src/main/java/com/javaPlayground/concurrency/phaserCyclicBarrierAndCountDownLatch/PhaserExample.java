package com.javaPlayground.concurrency.phaserCyclicBarrierAndCountDownLatch;


import java.util.concurrent.Phaser;

public class PhaserExample {

    public static void main(String[] args) {
        Phaser phaser = new Phaser(1); // register main

        // Worker 1 & 2 start immediately
        new Thread(new Worker(phaser, "A")).start();
        new Thread(new Worker(phaser, "B")).start();

        sleep(500);

        // Worker 3 joins late (dynamic registration)
        System.out.println("Worker C joining late...\n");
        new Thread(new Worker(phaser, "C")).start();

        // Main participates in the phases too
        runPhase("Main", phaser);

        // Deregister main
        phaser.arriveAndDeregister();
    }

    static void runPhase(String name, Phaser phaser) {
        for (int phase = 0; phase < 3; phase++) {
            System.out.println("[" + name + "] waiting for phase " + phase);
            phaser.arriveAndAwaitAdvance();
        }
    }

    static void sleep(long ms) {
        try { Thread.sleep(ms); } catch (InterruptedException ignored) {}
    }

    static class Worker implements Runnable {
        Phaser phaser;
        String id;

        Worker(Phaser p, String id) {
            this.phaser = p;
            this.id = id;
            p.register();
        }

        public void run() {
            for (int phase = 0; phase < 3; phase++) {
                System.out.println("Worker " + id + " performing phase " + phase);
                sleep(300 + phase * 200);
                phaser.arriveAndAwaitAdvance();
            }
            phaser.arriveAndDeregister();
        }
    }
}

