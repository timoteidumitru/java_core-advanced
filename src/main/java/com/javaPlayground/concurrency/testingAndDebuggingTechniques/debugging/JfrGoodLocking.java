package com.javaPlayground.concurrency.testingAndDebuggingTechniques.debugging;

import java.util.concurrent.atomic.LongAdder;

public class JfrGoodLocking {

    static LongAdder counter = new LongAdder();

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 8; i++) {
            Thread.startVirtualThread(() -> {
                while (true) {
                    counter.increment();
                    simulateWork();
                }
            });
        }

        Thread.sleep(60_000);
    }

    static void simulateWork() {
        try { Thread.sleep(10); } catch (InterruptedException ignored) {}
    }
}

