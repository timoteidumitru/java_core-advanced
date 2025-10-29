package com.javaPlayground.lambda;

import java.util.Random;
import java.util.function.*;

public class Runner {
    public static void main(String[] args) {

        // ----------------------------------------------------------
        // Function<T, R> — Takes one input (T) and returns one result (R)
        // ----------------------------------------------------------
        // Example: Converts a person's name into a friendly greeting.
        Function<String, String> greetFunction = name -> "Hey " + name + ", welcome aboard!";
        String greeting = greetFunction.apply("Tim");
        System.out.println("=== Function Example ===");
        System.out.println("Input: \"Tim\"");
        System.out.println("Output: " + greeting + "\n");

        // ----------------------------------------------------------
        // Predicate<T> — Takes one input (T) and returns a boolean (true/false)
        // ----------------------------------------------------------
        // Example: Checks whether a number is greater than 100.
        Predicate<Integer> isGreaterThan100 = number -> number > 100;
        int testValue = 110;
        System.out.println("=== Predicate Example ===");
        System.out.println("Is " + testValue + " greater than 100? " + isGreaterThan100.test(testValue) + "\n");

        // ----------------------------------------------------------
        // Supplier<T> — Takes no input but supplies (returns) an object of type T
        // ----------------------------------------------------------
        // Example: Supplies a random integer each time it's called.
        Supplier<Integer> randomNumberSupplier = () -> new Random().nextInt(1000); // limit to 0–999 for clarity
        System.out.println("=== Supplier Example ===");
        System.out.println("Generated random number: " + randomNumberSupplier.get());
        System.out.println("Generated another random number: " + randomNumberSupplier.get() + "\n");

        // ----------------------------------------------------------
        // Consumer<T> — Takes one input (T), performs an action, but does not return anything
        // ----------------------------------------------------------
        // Example: Prints a personalized message to the console.
        Consumer<String> printGreetingConsumer = name -> System.out.println("Hey " + name + ", have a great day!");
        System.out.println("=== Consumer Example ===");
        printGreetingConsumer.accept("Alice");
        printGreetingConsumer.accept("Bob");
        System.out.println();

        // ----------------------------------------------------------
        // BiFunction<T, U, R> — Takes two inputs (T and U) and returns one result (R)
        // ----------------------------------------------------------
        // Example: Calculates the average of two numbers.
        BiFunction<Integer, Integer, Double> averageCalculator = (a, b) -> (a + b) / 2.0;
        int x = 10, y = 30;
        System.out.println("=== BiFunction Example ===");
        System.out.println("Average of " + x + " and " + y + " = " + averageCalculator.apply(x, y));
    }
}
