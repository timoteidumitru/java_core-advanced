package com.javaPlayground.concurrency.javaMemoryModel;

public class UnsafePublication {
    static class Widget {
        final int x;
        int y;
        public Widget(int x, int y) {
            this.x = x;
            // simulate work
            this.y = y;
        }
    }

    static Widget shared;
//    volatile static Widget safeShared; // volatile → safe

    public static void main(String[] args) throws Exception {
        Thread writer = new Thread(() -> {
//            safeShared = new Widget(1,2); // published via volatile → safe
            shared = new Widget(1, 2); // unsafe publish
        });

        Thread reader = new Thread(() -> {
            while (shared == null) { Thread.yield(); }
//            while (safeShared == null) { Thread.yield(); } // published via volatile → safe
            System.out.println("x=" + shared.x + " y=" + shared.y);
        });

        writer.start(); reader.start();
        writer.join(); reader.join();
    }
}

