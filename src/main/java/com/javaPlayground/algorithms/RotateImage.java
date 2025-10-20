package com.javaPlayground.algorithms;

import java.util.Arrays;

public class RotateImage {
    public void rotate(int[][] matrix) {
        int n = matrix.length;

        // Step 1: Transpose the matrix
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                // Swap matrix[i][j] with matrix[j][i]
                var temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }

        // Step 2: Reverse each row
        for (var row : matrix) {
            int nCols = row.length;
            for (int j = 0; j < nCols / 2; j++) {
                // Swap row[j] with row[nCols - 1 - j]
                var temp = row[j];
                row[j] = row[nCols - 1 - j];
                row[nCols - 1 - j] = temp;
            }
        }
    }

    private static void printMatrix(int[][] matrix) {
        // Use streams for printing
        Arrays.stream(matrix)
                .map(Arrays::toString) // Convert row to string
                .forEach(System.out::println);   // Print each row
    }

    public static void main(String[] args) {
        RotateImage resolve = new RotateImage();

        int[][] matrix = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };

        System.out.println("Original Matrix:");
        printMatrix(matrix);

        resolve.rotate(matrix);
        System.out.println("\nFirst rotation:");
        printMatrix(matrix);

        resolve.rotate(matrix);
        System.out.println("\nSecond rotation:");
        printMatrix(matrix);

        resolve.rotate(matrix);
        System.out.println("\nThird Rotation:");
        printMatrix(matrix);
    }
}

