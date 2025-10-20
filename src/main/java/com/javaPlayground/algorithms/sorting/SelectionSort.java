package com.javaPlayground.algorithms.sorting;

import java.util.Arrays;

public class SelectionSort {
    public static void main(String[] args) {
        DataGenerator dataGenerator = new DataGenerator();
        int[] array = dataGenerator.generateUniqueRandomNumbers(100);
        System.out.println("Array before sorting: " + Arrays.toString(array));
        selectSort(array);
        System.out.println("Array after sorting: " + Arrays.toString(array));
    }

    public static void selectSort(int[] array){
        int size = array.length;
        int minIndex;
        int temp;
        int steps = 0;

        for (int i=0;i<size-1;i++){
            minIndex = i;
            for (int j=i+1;j<size;j++){
                if (array[minIndex] > array[j]){
                    minIndex = j;
                }
            }
            steps++;
            temp = array[minIndex];
            array[minIndex] = array[i];
            array[i] = temp;
        }
        System.out.println("Number of iterations: " + steps);
    }
}
