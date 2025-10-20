package com.javaPlayground.algorithms;

import java.util.*;
import java.util.stream.IntStream;

public class FindSumOfTwoNumbers {
    public static void main(String[] args) {
        findSumOfTwoNumbers(11);
        findSumOfTwoNumbersWithStreams(21);
    }

    public static void findSumOfTwoNumbers(int target){
        int[] nums = {2,3,4,5,6,7,9,12,13,14,15,17};
        int left = nums[0];
        int right = nums.length-1;

        System.out.println("Find " + target + " by adding 2 numbers in the following array: " + Arrays.toString(nums));
        for (int i=0;i<=nums.length-1;i++){
            for (int j=right;j>=left;j--){
                if (nums[i] + nums[j] == target && nums[i] < nums[j]){
                    System.out.println("Numbers found: " + nums[i] + " + " + nums[j] + " = " + (nums[i] + nums[j]));
                }
            }
        }
    }

    public static void findSumOfTwoNumbersWithStreams(int target) {
        int[] nums = {2, 3, 4, 5, 6, 7, 9, 12, 13, 14, 15, 17};

        System.out.println("\nFind " + target + " by adding 2 numbers in the following array: " + Arrays.toString(nums));

        // Use IntStream to loop through the indices
        IntStream.range(0, nums.length)
                .forEach(i -> IntStream.range(i + 1, nums.length)  // Start from i + 1 to avoid duplicates and reverse looping
                        .filter(j -> nums[i] + nums[j] == target)  // Filter pairs where the sum equals the target
                        .forEach(j -> System.out.println(
                                "Numbers found: " + nums[i] + " + " + nums[j] + " = " + (nums[i] + nums[j]))));
    }
}
