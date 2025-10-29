package com.javaPlayground.concurrency.syncronizedThreads;

import java.util.concurrent.CompletableFuture;
import java.util.function.IntPredicate;

public class Runner {
    public static void main(String[] args) throws InterruptedException {
        // Using Java7 approach
        Object object = new Object();
        Runnable first = new EvenAndOddPrinterWithJavaSeven(object);
        Runnable second = new EvenAndOddPrinterWithJavaSeven(object);
        new Thread(first, "even").start();
        new Thread(second, "odd").start();

        // Using Java8 approach
//        ExecutorService executorService = Executors.newFixedThreadPool(2);
//        EvenAndOddPrinterWithJavaEight executorsApproach = new EvenAndOddPrinterWithJavaEight(executorService);
//        executorsApproach.usingExecutors();

        // Using Completable Future with Streams
//        EvenAndOddsPrinterWithCF usingStreamsWithCF = new EvenAndOddsPrinterWithCF();
//        IntPredicate evenCondition = value -> value % 2 == 0;
//        IntPredicate oddCondition = value -> value % 2 != 0;
//        CompletableFuture.runAsync(() -> usingStreamsWithCF.printResult(oddCondition));
//        CompletableFuture.runAsync(() -> usingStreamsWithCF.printResult(evenCondition));
//        Thread.sleep(7000);
    }
}
