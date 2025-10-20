package com.javaPlayground.algorithms.binarySearch;

import java.util.ArrayList;
import java.util.List;

public class BinarySearch {
    public static void main(String[] args) {
        List<Integer> array = generateArr();
        int target = 2;

//        binarySearch(array, target);
        improverBinarySearch(array, target);

    }

    public static void binarySearch(List<Integer> arr, int target){

        if (arr.size() < target){
            System.out.println("Out of bound value.");
            return;
        }

        int left = 0;
        int right = arr.size();
        int count = 0;

        while(left <= right){
            int mid = (left + right) / 2;
            if(arr.get(mid) == target){
                System.out.println("~~~ Target found after " + count + " steps ~~~");
                return;
            }else if(arr.get(mid) < target){
                count++;
                left = mid + 1;
                System.out.println("Right half, step: " + count);
            } else {
                count++;
                right = mid - 1;
                System.out.println("Left half, step: " + count);
            }
        }
        System.out.println("Element has not been found!");
    }

    public static void improverBinarySearch(List<Integer> arr, int tar){
        int iterations = 0, left = 0;
        int right = arr.size() - 1;

        while(left <= right){
            iterations++;
            int mid = left + (right - left) / 2;
            if(arr.get(mid) == tar){
                System.out.println("Target "+tar+" found after "+iterations+" iterations");
                return;
            }else if(arr.get(mid) < tar) {
                left = mid + 1;
            }else{
                right = mid - 1;
            }
        }

        System.out.println("Target "+tar+" could not be found in the following data: \n"+arr);
    }
           // L     M     R
    public static ArrayList<Integer> generateArr(){
        ArrayList<Integer> arrList = new ArrayList<>();
        for(int i=0;i<=100;i++){
            arrList.add(i);
        }
        return arrList;
    }
}
