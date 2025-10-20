package com.javaPlayground.algorithms;

import java.util.*;

public class CalculateProfit {
    public static void main(String[] args) {
        // Initialize data and trading days
        List<Integer> stockPrices = Arrays.asList(7, 1, 5, 3, 6, 4);
        int buyDay = 2;
        int sellDay = 5;

        // Calculate and display the profit
        calculateProfit(stockPrices, buyDay, sellDay);
    }

    private static void calculateProfit(List<Integer> stockPrices, int buyDay, int sellDay) {
        // Create a map of day indices to stock prices
        Map<Integer, Integer> dayToPriceMap = new HashMap<>();
        for (int i = 0; i < stockPrices.size(); i++) {
            dayToPriceMap.put(i + 1, stockPrices.get(i)); // Use 1-based index for days
        }

        // Display the data and calculate profit
        System.out.println("Stock Prices by Day: " + stockPrices);
        System.out.println("Buy on Day: " + buyDay + " | Sell on Day: " + sellDay);

        int profit = dayToPriceMap.get(sellDay) - dayToPriceMap.get(buyDay);
        System.out.println("Profit: " + profit);
    }
}
