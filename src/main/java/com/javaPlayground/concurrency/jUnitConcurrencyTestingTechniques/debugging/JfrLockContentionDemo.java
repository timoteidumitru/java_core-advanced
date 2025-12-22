package com.javaPlayground.concurrency.jUnitConcurrencyTestingTechniques.debugging;

public class JfrLockContentionDemo {

    private static final Object LOCK = new Object();

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 8; i++) {
            new Thread(() -> {
                while (true) {
                    synchronized (LOCK) {
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException ignored) {}
                    }
                }
            }, "worker-" + i).start();
        }

        Thread.sleep(10_000);
    }
}

