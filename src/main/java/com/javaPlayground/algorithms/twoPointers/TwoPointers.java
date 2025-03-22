package com.javaPlayground.algorithms.twoPointers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TwoPointers {
    public static void main(String[] args) {
        List<Integer> input = Arrays.asList(1,2,4,5,7,8,9,11,12,13,14,17);
        Integer target = 19;

        twoSum(input, target);
    }

    private static void twoSum(List<Integer> arr, Integer tar) {
        int left = 0, right = arr.size()-1;
        List<List<Integer>> result = new ArrayList<>();

        while (left < right){
            int sum = arr.get(left) + arr.get(right);

            if(sum < tar) left++;
            else if (sum > tar) right--;
            else {
                result.add(Arrays.asList(arr.get(left), arr.get(right)));
                left++;
                right--;
            }
        }
        System.out.println("Result: " + result);
    }
}
