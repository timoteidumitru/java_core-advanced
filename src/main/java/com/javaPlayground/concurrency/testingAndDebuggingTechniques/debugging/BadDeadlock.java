package com.javaPlayground.concurrency.testingAndDebuggingTechniques.debugging;

public class BadDeadlock {

    static final Object A = new Object();
    static final Object B = new Object();

    public static void main(String[] args) {
        new Thread(() -> {
            synchronized (A) {
                sleep();
                synchronized (B) {}
            }
        }, "T1").start();

        new Thread(() -> {
            synchronized (B) {
                sleep();
                synchronized (A) {}
            }
        }, "T2").start();
    }

    static void sleep() {
        try { Thread.sleep(100); } catch (InterruptedException ignored) {}
    }
}
