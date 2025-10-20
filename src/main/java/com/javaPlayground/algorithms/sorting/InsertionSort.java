package com.javaPlayground.algorithms.sorting;

import java.util.Arrays;

public class InsertionSort {
    public static void main(String[] args) {
        DataGenerator dataGenerator = new DataGenerator();
        int[] array = dataGenerator.generateUniqueRandomNumbers(100);
        System.out.println("Array before sorting: " + Arrays.toString(array));
        insertionSort(array);
        System.out.println("Array after sorting: " + Arrays.toString(array));

    }

    public static void insertionSort(int[] array){
        int steps = 0;

        for (int i=1;i<array.length;i++){
            int key = array[i];
            int j = i-1;

            while (j>=0 && array[j] > key){
                array[j+1] = array[j];
                j--;
            }
            steps++;
            array[j+1] = key;
        }
        System.out.println("Number or iterations: " + steps);
    }

}
