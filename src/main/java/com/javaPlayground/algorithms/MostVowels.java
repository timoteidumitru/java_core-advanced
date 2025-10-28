package com.javaPlayground.algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MostVowels {
    public static void main(String[] args) {
        String str = "Let's find out the words with the most vowels in this string!";
        String vowels = "AEIOUaeiou";

//        findWordWithMostVowelsStandard(str, vowels);
        findWordWithMostVowelsStreams(str, vowels);
    }

    // streams approach
    private static void findWordWithMostVowelsStreams(String str, String vowels) {
        String[] words = str.split(" ");

        Map<Integer, List<String>> grouped = Arrays.stream(words)
                .collect(Collectors.groupingBy(
                        w -> (int) w.chars().filter(c -> vowels.indexOf(c) >= 0).count()
                ));

        int maxVowels = grouped.keySet().stream().max(Integer::compareTo).orElse(0);
        List<String> result = grouped.getOrDefault(maxVowels, List.of());

        System.out.println("Given data: " + str);
        System.out.println("Words with most vowels: " + String.join(" ", result));
    }

    // standard approach
    private static void findWordWithMostVowelsStandard(String str, String vowels) {
        String[] words = str.split(" ");
        int maxVowelsCount = 0;
        List<String> result = new ArrayList<>();

        for (String word : words) {
            int count = 0;
            for (char c : word.toCharArray()) {
                if (vowels.indexOf(c) >= 0) {
                    count++;
                }
            }

            if (count > maxVowelsCount) {
                maxVowelsCount = count;
                result.clear();
                result.add(word);
            } else if (count == maxVowelsCount) {
                result.add(word);
            }
        }

        System.out.println("Given data: " + str);
        System.out.println("Words with most vowels: " + result);
    }
}
