package com.javaPlayground.concurrency.syncronizedThreads;

public class EvenAndOddPrinterWithJavaSeven implements Runnable {
    private static int counter = 1;
    private final Object object;

    public EvenAndOddPrinterWithJavaSeven(Object object) {
        this.object = object;
    }

    @Override
    public void run() {
        while (counter<=5){
            if (counter%2==0 && Thread.currentThread().getName().equals("even")){
                synchronized (object){
                    System.out.println("Current thread name running '" + Thread.currentThread().getName() + "', have value: " + counter);
                    counter++;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    object.notify();
                }
            }
            if (counter%2!=0 && Thread.currentThread().getName().equals("odd")){
                synchronized (object){
                    System.out.println("Current thread name running '" + Thread.currentThread().getName() + "', have value: " + counter);
                    counter++;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    object.notify();
                }
            }
        }
    }
}
