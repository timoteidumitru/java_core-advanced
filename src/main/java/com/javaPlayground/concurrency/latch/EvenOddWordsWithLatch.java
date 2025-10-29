package com.javaPlayground.concurrency.latch;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class EvenOddWordsWithLatch {
    public static void main(String[] args) throws InterruptedException {
        List<String> words = Arrays.asList("One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten");
        final CountDownLatch[] evenLatch = {new CountDownLatch(1)};     // Starts locked for the even thread
        final CountDownLatch[] oddLatch = {new CountDownLatch(0)};      // Unlocked for the odd thread

        Thread evenThread = new Thread(() -> {
            for (int i = 0; i < words.size(); i += 2) {
                try {
                    Thread.sleep(1000);
                    evenLatch[0].await();                               // Wait for permission to print even word
                    System.out.println("Even Thread: " + words.get(i));
                    evenLatch[0] = new CountDownLatch(1);               // Reset latch for next iteration
                    oddLatch[0].countDown();                            // Signal odd thread
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        Thread oddThread = new Thread(() -> {
            for (int i = 1; i < words.size(); i += 2) {
                try {
                    Thread.sleep(1000);
                    oddLatch[0].await();                                    // Wait for permission to print odd word
                    System.out.println("Odd Thread: " + words.get(i));
                    oddLatch[0] = new CountDownLatch(1);                    // Reset latch for next iteration
                    evenLatch[0].countDown();                               // Signal even thread
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        evenThread.start();
        oddThread.start();
    }
}

