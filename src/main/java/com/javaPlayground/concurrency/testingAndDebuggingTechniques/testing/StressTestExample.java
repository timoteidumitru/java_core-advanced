package com.javaPlayground.concurrency.testingAndDebuggingTechniques.testing;

public class StressTestExample {

    static volatile int counter = 0;

    public static void main(String[] args) throws Exception {
        for (int run = 0; run < 1000; run++) {
            counter = 0;

            Thread t1 = new Thread(() -> increment());
            Thread t2 = new Thread(() -> increment());

            t1.start();
            t2.start();
            t1.join();
            t2.join();

            if (counter != 2000) {
                System.out.println("Race detected at run " + run);
                break;
            }
        }
        System.out.println("Stress test finished");
    }

    static void increment() {
        for (int i = 0; i < 1000; i++) {
            counter++; // NOT atomic
        }
    }
}

