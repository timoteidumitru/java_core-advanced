package com.javaPlayground.algorithms;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FindOccurrenceInAString {
    public static void main(String[] args) {
        String input = "JavaStreamsAreFun";

        Map<String, Long> count = Arrays.stream(input.split(""))
                .collect(Collectors
                            .groupingBy(Function.identity(), Collectors.counting()));

        System.out.println(count);
    }
}
