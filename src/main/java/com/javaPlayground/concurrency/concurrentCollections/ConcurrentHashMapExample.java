package com.javaPlayground.concurrency.concurrentCollections;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;

public class ConcurrentHashMapExample {
    public static void main(String[] args) throws InterruptedException {
        Map<String, Integer> freq = new ConcurrentHashMap<>();

        var exec = Executors.newFixedThreadPool(4);
        String[] words = {"apple", "banana", "apple", "orange", "banana", "apple"};

        for (String word : words) {
            exec.submit(() -> {
                freq.merge(word, 1, Integer::sum);
            });
        }

        exec.shutdown();
        while (!exec.isTerminated()) {}

        System.out.println(freq);
        // Example Output: {orange=1, banana=2, apple=3}
    }
}

