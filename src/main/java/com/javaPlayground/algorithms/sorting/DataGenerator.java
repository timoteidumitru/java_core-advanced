package com.javaPlayground.algorithms.sorting;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class DataGenerator {
    public int[] generateUniqueRandomNumbers(int target) {
        if (target > 1_000 || target < 1) {
            throw new IllegalArgumentException("Target must be between 1 and 1,000.");
        }
        Random random = new Random();
        Set<Integer> uniqueNumbers = new HashSet<>();
        int[] randomNumbers = new int[target];

        int index = 0;
        while (uniqueNumbers.size() < target) {
            int number = random.nextInt(target) + 1;
            if (uniqueNumbers.add(number)) {
                randomNumbers[index++] = number;
            }
        }

        return randomNumbers;
    }
}
