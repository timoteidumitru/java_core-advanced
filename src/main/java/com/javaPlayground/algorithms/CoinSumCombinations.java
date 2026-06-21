package com.javaPlayground.algorithms;

import java.util.Scanner;

public class CoinSumCombinations {

    static int numberOfDenominations;
    static int targetAmount;

    static int[] denominations;
    static int[] banknoteCounts;

    void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("======================================");
        System.out.println("      BANKNOTE COMBINATION FINDER");
        System.out.println("======================================");

        System.out.print("\nEnter the target amount: ");
        targetAmount = scanner.nextInt();

        System.out.print("Enter the number of denominations: ");
        numberOfDenominations = scanner.nextInt();

        denominations = new int[numberOfDenominations];
        banknoteCounts = new int[numberOfDenominations];

        System.out.println("\nEnter the denomination values:");

        for (int i = 0; i < numberOfDenominations; i++) {
            System.out.print("Denomination #" + (i + 1) + ": ");
            denominations[i] = scanner.nextInt();
        }

        System.out.println("\n======================================");
        System.out.println("Solutions for amount " + targetAmount + ":");
        System.out.println("======================================\n");

        backtrack(0, 0);
    }

    static void printSolution() {

        for (int i = 0; i < numberOfDenominations; i++) {

            if (banknoteCounts[i] > 0) {

                System.out.print(
                        STR."\{banknoteCounts[i]}x\{denominations[i]}\{denominations[i] == 1 ? "dollar " : "dollars "}"
                );
            }
        }

        System.out.println();
    }

    static void backtrack(int index, int currentSum) {

        if (index == numberOfDenominations) {

            if (currentSum == targetAmount) {
                printSolution();
            }

            return;
        }

        int maxCount = (targetAmount - currentSum) / denominations[index];

        for (int count = 0; count <= maxCount; count++) {

            banknoteCounts[index] = count;

            backtrack(
                    index + 1,
                    currentSum + count * denominations[index]
            );
        }
    }

}

