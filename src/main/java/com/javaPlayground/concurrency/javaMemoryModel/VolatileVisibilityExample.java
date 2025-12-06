package com.javaPlayground.concurrency.javaMemoryModel;

public class VolatileVisibilityExample {

    static class Worker extends Thread {
        private volatile boolean running = true;

        void shutdown() { running = false; }

        @Override
        public void run() {
            long count = 0;
            while (running) { // if running not volatile, loop might never stop
                count++;
            }
            System.out.println("Loop ended, count=" + count);
        }

        public static void main(String[] args) throws Exception {
            Worker w = new Worker();
            w.start();
            Thread.sleep(100);
            w.shutdown();
            w.join();
            System.out.println("Worker joined.");
        }
    }

}
