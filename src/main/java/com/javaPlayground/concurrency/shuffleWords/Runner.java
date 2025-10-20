package com.javaPlayground.concurrency.shuffleWords;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Runner {
    public static void main(String[] args) {
        String input = "Hey, lets try to shuffle this string using concurrency to access each work randomly via different threads for each iteration.";

        String[] words = input.split(" ");
        ExecutorService service = Executors.newFixedThreadPool(5);

        for (String word : words){
            service.submit(() -> {
                processWord(word);
            });
        }

        service.shutdown();
    }

    public static void processWord(String word) {
        try {
            Thread.sleep(1000);
            System.out.println("Thread: " + Thread.currentThread()
                    .getName().split("pool-1-thread-")[1] + " processed word: " + word);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
