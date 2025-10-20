package com.javaPlayground.algorithms;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GroupWordsByLength {
    public static void main(String[] args) {
        List<String> list = Arrays.asList("Have", "fun", "with", "java", "collections", "using", "streams", "javaFun");
        groupWordsByLength(list);
    }

    public static void groupWordsByLength(List<String> list) {

        Map<Integer, List<String>> result = list
                .stream()
                .collect(Collectors.groupingBy(String::length,
                        Collectors.mapping(Function.identity(), Collectors.toList())));

        result.forEach((c,w) -> {
            System.out.println("Strings of length "+c+": " + w.size() + (w.size() < 2 ? " string": " strings") );
        });
    }
}
