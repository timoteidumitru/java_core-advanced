package com.javaPlayground.concurrency.syncronizedThreads;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.stream.IntStream;

public class EvenAndOddPrinterWithJavaEight {
     private final ExecutorService executorService;

    public EvenAndOddPrinterWithJavaEight(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public void usingExecutors(){
        IntStream.rangeClosed(1, 5)
                .forEach(el -> {
                    CompletableFuture<Integer> oddCompletableFuture = CompletableFuture.completedFuture(el)
                            .thenApplyAsync(x -> {
                                if (x % 2 != 0){
                                    System.out.println("Thread name '" + Thread.currentThread().getName().toUpperCase() + "', with value: " + x);
                                }
                                return el;
                            }, executorService);
                    oddCompletableFuture.join();

                    CompletableFuture<Integer> evenCompletableFuture = CompletableFuture.completedFuture(el)
                            .thenApplyAsync(x -> {
                                if (x % 2 == 0){
                                    System.out.println("Thread name '" + Thread.currentThread().getName().toUpperCase() + "', with value: " + x);
                                }
                                return el;
                            }, executorService);
                    evenCompletableFuture.join();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                });
        executorService.shutdown();
    }
}
