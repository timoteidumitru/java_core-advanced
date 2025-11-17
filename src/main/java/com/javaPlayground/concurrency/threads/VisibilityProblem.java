package com.javaPlayground.concurrency.threads;

class VisibilityProblem {

//    private static volatile boolean running = true;  // -> Writes to a volatile are immediately visible
    private static boolean running = true;

    public static void main(String[] args) throws Exception {
        new Thread(() -> {
            while (running) {}     // never stops, cached value
        }).start();

        Thread.sleep(1000);
        running = false;           // write not visible to the other thread
    }

//      The worker thread may never stop, because it may cache running in its CPU register.
//      No visibility guarantee!
}
