package com.javaPlayground.algorithms;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class LettersCounterFromWord {
    public static void main(String[] args) {
        List<String> words = Arrays.asList("banana", "apple", "strawberry", "piece", "blueberry", "mango");

        Map<Integer, List<String>> lettersCounter = words.stream()
                .collect(Collectors.groupingBy(String::length,
                        Collectors.mapping(Function.identity(), Collectors.toList())));

        System.out.println(lettersCounter);
    }
}
