package com.javaPlayground.algorithms;

import java.util.Arrays;
import java.util.List;

public class PermuteNums {
    public static void main(String[] args) {
        List<Integer> input = Arrays.asList(1,2,3);
        int right = input.size();

        permuteNums(input, 0, right);
    }

    private static void permuteNums(List<Integer> input, int left, int right) {
        if (left == right) {
            for (int i : input){
                System.out.print(i + " ");
            }
            System.out.println();
        } else {
            for (int i=left; i<right; i++){
                swapNums(input, left, i);

                permuteNums(input, left+1, right);

                swapNums(input, left, i);
            }
        }
    }

    private static void swapNums(List<Integer> arr, int a, int b){
        int temp = arr.get(a);
        arr.set(a, arr.get(b));
        arr.set(b, temp);
    }

}
