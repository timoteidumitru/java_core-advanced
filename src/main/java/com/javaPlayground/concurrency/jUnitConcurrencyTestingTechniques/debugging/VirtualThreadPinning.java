package com.javaPlayground.concurrency.jUnitConcurrencyTestingTechniques.debugging;

public class VirtualThreadPinning {

    static synchronized void blocking() {
        try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10_000_000; i++) {
            Thread.startVirtualThread(VirtualThreadPinning::blocking);
        }
    }
}

