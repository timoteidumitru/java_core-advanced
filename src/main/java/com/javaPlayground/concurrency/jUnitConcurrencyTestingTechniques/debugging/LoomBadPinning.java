package com.javaPlayground.concurrency.jUnitConcurrencyTestingTechniques.debugging;

//VirtualThreadPinned
//Carrier starvation
//Throughput collapse
public class LoomBadPinning {

    static synchronized void blockingCall() {
        try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100_000; i++) {
            Thread.startVirtualThread(LoomBadPinning::blockingCall);
        }
    }
}

