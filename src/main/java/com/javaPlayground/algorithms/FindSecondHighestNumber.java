package com.javaPlayground.algorithms;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class FindSecondHighestNumber {
    public static void main(String[] args) {
        List<Integer> nums = Arrays.asList(4,1,5,7,4,6,3);
        findSecondHighestNumber(nums, 2);
    }

    public static void findSecondHighestNumber(List<Integer> nums, int index){
        Integer result = nums.stream().distinct().sorted(Comparator.reverseOrder()).toList().get(index - 1);
        System.out.println("Input: " + nums);
        System.out.println(index + "nd highest number is: " + result);
    }
}
