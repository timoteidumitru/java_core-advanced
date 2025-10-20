package com.javaPlayground.algorithms;

import java.util.Arrays;
import java.util.List;

public class FindMissingNumber {
    public static void main(String[] args) {
        List<Integer> nums = Arrays.asList(3,1,6,2,4,7,9,3);
        findMissingNumber(nums);
    }

    public static void findMissingNumber(List<Integer> nums){
        int foundElem = 0;
        List<Integer> sortedNums = nums.stream().sorted().distinct().toList();
        System.out.println(sortedNums);

        for (int i=0;i<sortedNums.size();i++){
            if (sortedNums.get(i + 1) != sortedNums.get(i) + 1) {
                foundElem = sortedNums.get(i) +1;
                break;
            }
        }

        System.out.println(
                foundElem == 0
                ? "No consecutive element missing in the list"
                : "element " + foundElem + " is missing from the list"
        );
    }
}
