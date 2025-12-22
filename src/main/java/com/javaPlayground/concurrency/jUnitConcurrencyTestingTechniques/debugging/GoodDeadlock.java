package com.javaPlayground.concurrency.jUnitConcurrencyTestingTechniques.debugging;

public class GoodDeadlock {

    static final Object A = new Object();
    static final Object B = new Object();

    public static void main(String[] args) {
        Runnable task = () -> {
            synchronized (A) {
                synchronized (B) {}
            }
        };

        new Thread(task).start();
        new Thread(task).start();
    }
}

