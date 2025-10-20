package com.javaPlayground.concurrency.simplemultithreading;

public class Runner {
    public static void main(String[] args) {
        MultithreadWithThread t1 = new MultithreadWithThread("Thread - 1");
        MultithreadWithThread t2 = new MultithreadWithThread("Thread - 2");
        MultithreadWithThread t3 = new MultithreadWithThread("Thread - 3");

        t1.start();
        t2.start();
        t3.start();

//        Thread tr1 = new Thread(new MultithraedWithRunnable("Thread - 1"));
//        Thread tr2 = new Thread(new MultithraedWithRunnable("Thread - 2"));
//
//        tr1.start();
//        tr2.start();
    }
}
