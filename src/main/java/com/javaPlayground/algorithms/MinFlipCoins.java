package com.javaPlayground.algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MinFlipCoins {
    public static void main(String[] args) {
        List<Integer> input = Arrays.asList(1, 2, 3, 5, 7, 9);
        int target = 4;
        List<Integer> result = findMinFlips(input, target);

        System.out.println("Input data: " + input);
        System.out.println("Minimum coins to flip to get target " + target + " is: " + result);
    }

    public static List<Integer> findMinFlips(List<Integer> input, int target) {
        List<Integer> result = new ArrayList<>();
        backtrack(input, target, 0, new ArrayList<>(), 0, result);
        return result;
    }

    private static void backtrack(List<Integer> input, int target, int index, List<Integer> current, int currentSum, List<Integer> result) {
        if (currentSum == target) {
            if (result.isEmpty() || current.size() < result.size()) {
                result.clear();
                result.addAll(new ArrayList<>(current));
            }
            return;
        }

        if (currentSum > target || index >= input.size()) {
            return;
        }

        current.add(input.get(index));
        backtrack(input, target, index + 1, current, currentSum + input.get(index), result);
        current.remove(current.size() - 1); // Backtrack
        backtrack(input, target, index + 1, current, currentSum, result);
    }
}
