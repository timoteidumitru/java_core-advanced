package com.javaPlayground.concurrency.completableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;

public class UsingCompletableFuture {
    private static final Object obj = new Object();
    private static final IntPredicate even = e -> e % 2 == 0;
    private static final IntPredicate odd = e -> e % 2 != 0;

    public static void main(String[] args) {
        CompletableFuture.runAsync(() -> UsingCompletableFuture.printResult(odd));
        CompletableFuture.runAsync(() -> UsingCompletableFuture.printResult(even));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void printResult(IntPredicate condition){
        IntStream.rangeClosed(1, 10)
                .filter(condition)
                .forEach(UsingCompletableFuture::execute);
    }

    private static void execute(int i){
        synchronized (obj){
            try {
                System.out.println("Thread name: \"" + Thread.currentThread()
                        .getName().replaceAll(".commonPool-worker", "") + "\", with value: " + i);
                obj.notify();
                obj.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
