package com.javaPlayground.algorithms;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MostFrequentWord {
    public static void main(String[] args) {
        String input = "We gonna use this string as a test to see what word is most frequent and for this we need to use java8 with streams api use use.";
        findMostFrequentWord(input);
    }

    private static void findMostFrequentWord(String input) {
        // Convert input to lowercase, split by whitespace, and group by word with counts
        Map<String, Long> wordCounts = Arrays.stream(input.toLowerCase().split("\\s+"))
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        // Find the maximum frequency
        long maxFrequency = wordCounts.values().stream().mapToLong(Long::longValue).max().orElse(0);

        // Collect words with the maximum frequency
        List<String> mostFrequentWords = wordCounts.entrySet().stream()
                .filter(entry -> entry.getValue() == maxFrequency)
                .map(Map.Entry::getKey)
                .toList();

        // Output the result
        System.out.println("Most frequent word(s): " + mostFrequentWords);
        System.out.println("Frequency: " + maxFrequency);
    }
}
