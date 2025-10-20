package com.javaPlayground.algorithms;

public class PercentageMultiplierCalculator {
    public static void main(String[] args) {

        int invest = 100;
        int days = 120;
        double percentage = 0.02;

        calculatePercentageMultiplier(invest, days, percentage);
    }

    public static void calculatePercentageMultiplier(int invest, int days, double percent) {
        double result = invest;

        for (int i = 1; i <= days; i++) {
            result += result * percent;

            System.out.println("Day " + i + ", result after multiply: " + round(result));
        }

        System.out.println("\nFinal result: " + round(result));
    }

    public static double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

}
