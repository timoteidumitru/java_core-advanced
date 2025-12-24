package com.javaPlayground.concurrency.testingAndDebuggingTechniques.debugging;

public class JfrBadLocking {

    private static final Object LOCK = new Object();

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 8; i++) {
            Thread.ofPlatform().start(() -> {
                while (true) {
                    synchronized (LOCK) {
                        expensiveWork();
                    }
                }
            });
        }

        Thread.sleep(60_000);
    }

    static void expensiveWork() {
        try { Thread.sleep(10); } catch (InterruptedException ignored) {}
    }
}

