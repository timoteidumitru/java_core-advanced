package com.javaPlayground.algorithms.twoPointers;

import java.util.*;

public class TwoPointers {
    public static void main(String[] args) {
        List<Integer> input = Arrays.asList(1,2,4,5,7,8,9,11,12,13,14,17);
        List<Integer> unsortedArr = Arrays.asList(4,1,3,2,5,6,7,8,11,9,12);
        Integer target = 16;

//        twoSum(input, target);
        twoSumII(unsortedArr, target);
    }

    private static void twoSumII(List<Integer> arr, Integer tar) {
        HashSet<Integer> seen = new HashSet<>();
        List<List<Integer>> result = new ArrayList<>();

        arr.forEach(e ->  {
           int complement = tar - e;
           if (seen.contains(complement)){
               result.add(Arrays.asList(e, complement));
           }
           seen.add(e);
        });

        System.out.println("Result: " + result);
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
