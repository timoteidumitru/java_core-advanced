package com.javaPlayground.concurrency.concurrencyBestPractices;


public class StatelessServiceExample {

    // A Stateless service is a class whose methods depend only on input parameters, without stored state.
    public static class PriceCalculatorService {

        public double applyDiscount(double price, double percent) {
            return price - (price * percent);
        }
    }

}
