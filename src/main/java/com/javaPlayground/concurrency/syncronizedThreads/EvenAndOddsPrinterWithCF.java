package com.javaPlayground.concurrency.syncronizedThreads;

import java.util.function.IntPredicate;
import java.util.stream.IntStream;

public class EvenAndOddsPrinterWithCF {
    private static final Object object = new Object();

    public void printResult(IntPredicate condition){
        IntStream.rangeClosed(1, 5)
                .filter(condition)
                .forEach(EvenAndOddsPrinterWithCF::execute);
    }

    public static void execute(int i) {
        synchronized (object){
            try {
                System.out.println("Thread name '" + Thread.currentThread().getName() + "', with value: " + i);
                Thread.sleep(1000);
                object.notify();
                object.wait();
            } catch (InterruptedException ex){
                // handle exception
            }

        }
    }
}
