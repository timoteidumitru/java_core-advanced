package com.javaPlayground.algorithms;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ContainDuplicates {
    public static void main(String[] args) {
        List<Integer> nums = Arrays.asList(1,2,3,4,5,3,6,7,3,3,1,3,2,2,7,7,7,7);
//        containDuplicates(nums);
        containDuplicate(nums);
    }

    // Java7 version
    public static void containDuplicates(List<Integer> nums){
        Map<Integer, Integer> mappedNums = new HashMap<>();
        boolean containDuplicates = true;

        for (Integer num : nums) {
            if (!mappedNums.containsKey(num)) {
                mappedNums.put(num, 1);
                containDuplicates = false;
            } else {
                mappedNums.put(num, mappedNums.get(num) + 1);
                containDuplicates = true;
            }
        }

        System.out.println(mappedNums + " -> " + containDuplicates);
    }

    // Java8 version, using streams
    public static void containDuplicate(List<Integer> nums) {
        Map<Integer, Long> mappedNums = nums.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        boolean containDuplicates = mappedNums.values().stream().anyMatch(count -> count > 1);

        System.out.println(mappedNums + " -> " + containDuplicates);
    }
}
