package com.javaPlayground.algorithms;

import java.util.ArrayList;
import java.util.List;

public class FindMedian {

    private final List<Integer> numbers;

    public FindMedian() {
        numbers = new ArrayList<>();
    }

    public void addNum(int num){
        numbers.add(num);
    }

    public double findMedian() {
        // Sort the list of numbers
        List<Integer> sortedNumbers = numbers.stream().sorted().toList();
        int size = sortedNumbers.size();

        if (size % 2 == 1) {
            // Odd number of elements, return the middle one
            return sortedNumbers.get(size / 2);
        } else {
            // Even number of elements, return the average of the two middle elements
            int mid1 = size / 2 - 1;
            int mid2 = size / 2;
            return (sortedNumbers.get(mid1) + sortedNumbers.get(mid2)) / 2.0;
        }
    }
    public static void main(String[] args) {
        FindMedian fm = new FindMedian();
//        fm.addNum(1);
//        fm.addNum(2);
//        System.out.println(fm.findMedian());
        fm.addNum(7);
        fm.addNum(1);
        fm.addNum(3);
        fm.addNum(4);
        System.out.println(fm.findMedian());
    }
}
