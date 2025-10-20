package com.javaPlayground.algorithms.sorting;

import java.util.Arrays;

public class BubbleSort {
    public static void main(String[] args) {
        DataGenerator dataGenerator = new DataGenerator();
        int[] array = dataGenerator.generateUniqueRandomNumbers(100);

        System.out.println("Array before sorting: " + Arrays.toString(array));
        bubbleSort(array);
        System.out.println("Array after sorting: " + Arrays.toString(array));

    }
    public static void bubbleSort(int[] array){
        int size = array.length;
        int temp;
        int steps = 0;

        for (int i=0;i<size;i++){
            for (int j=0;j<array.length-i-1;j++){
                if (array[j] > array[j+1]){
                    steps++;
                    temp = array[j];
                    array[j] = array[j+1];
                    array[j+1] = temp;
                }
            }
        }
        System.out.println("Number of iterations: " + steps);
    }

}
