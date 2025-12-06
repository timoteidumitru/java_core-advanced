package com.javaPlayground.concurrency.javaMemoryModel;

public class ReorderingExample {

    static int x = 0, y = 0;
    static int r1 = 0, r2 = 0;

    public static void main(String[] args) throws Exception {
        for (long i = 0; i < 1_000_000; i++) {
            x = y = r1 = r2 = 0;
            Thread t1 = new Thread(() -> {
                x = 1;
                r1 = y;
            });
            Thread t2 = new Thread(() -> {
                y = 1;
                r2 = x;
            });
            t1.start(); t2.start();
            t1.join(); t2.join();
            if (r1 == 0 && r2 == 0) {
                System.out.println("Reordering observed at iteration " + i);
                break;
            }
        }
        System.out.println("Done");
    }

}
