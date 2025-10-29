package com.javaPlayground.functionalInterfaces;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;

public class Runner {

    public static void main(String[] args) {
//        dollarsToEuroConverter();   // Function: accepts input, returns a result
//        retirementEligibility();    // Predicate: evaluates condition, returns boolean
//        notificationSender();       // Consumer: consumes input, performs an action
        idGenerator();              // Supplier: supplies a result without input
    }

    // -----------------------------
    // Example of Supplier Interface
    // -----------------------------
    public static void idGenerator() {
        Supplier<String> generateOrderId = () -> UUID.randomUUID().toString();
        String newOrderId = generateOrderId.get();
        System.out.println("\n=== Supplier Example ===");
        System.out.println("UUID generated: " + newOrderId);
    }

    // -----------------------------
    // Example of Consumer Interface
    // -----------------------------
    public static void notificationSender() {
        EmailService emailService = new EmailService();

        Consumer<User> sendNotification = user ->
                emailService.send(
                        user.getEmail(),
                        "Notification Subject",
                        "Hey " + user.getName() + "! Just wanted to remind you of something important."
                );

        List<User> users = getUsers();

        System.out.println("\n=== Consumer Example ===");
        users.forEach(sendNotification);
    }

    // -----------------------------
    // Example of Predicate Interface
    // -----------------------------
    public static void retirementEligibility() {
        List<Employee> employees = getEmployees();

        Predicate<Employee> isEligibleForRetirement = employee -> employee.getAge() >= 55;

        List<Employee> eligibleEmployees = employees.stream()
                .filter(isEligibleForRetirement)
                .toList();

        System.out.println("\n=== Predicate Example ===");
        System.out.println("Employees eligible for retirement (age >= 55):");
        eligibleEmployees.forEach(System.out::println);
    }

    // -----------------------------
    // Example of Function Interface
    // -----------------------------
    public static void dollarsToEuroConverter() {
        Function<Double, Double> convertToEuros = priceInDollars -> priceInDollars * 0.85;

        List<Double> pricesInDollars = Arrays.asList(100.0, 200.0, 50.0);

        List<Double> pricesInEuros = pricesInDollars.stream()
                .map(convertToEuros)
                .toList();

        System.out.println("\n=== Function Example ===");
        System.out.println("Conversion from $ to €:");
        for (int i = 0; i < pricesInDollars.size(); i++) {
            System.out.printf("$%.2f → €%.2f%n", pricesInDollars.get(i), pricesInEuros.get(i));
        }
    }

    // -----------------------------
    // Mock Data and Helper Methods
    // -----------------------------
    private static List<Employee> getEmployees() {
        return Arrays.asList(
                new Employee(1, "John Doe", 60, "Finance"),
                new Employee(2, "Jane Smith", 45, "Marketing"),
                new Employee(3, "Mike Brown", 55, "IT"),
                new Employee(4, "Joseph Smith", 35, "Accounting"),
                new Employee(5, "James Cross", 44, "Construction"),
                new Employee(6, "Michael Allan", 50, "Transporting"),
                new Employee(7, "Allan McAllister", 64, "HR")
        );
    }

    private static List<User> getUsers() {
        return Arrays.asList(
                new User(1, "Alice Johnson", "alice.johnson@example.com"),
                new User(2, "Bob Smith", "bob.smith@example.com"),
                new User(3, "Charlie Brown", "charlie.brown@example.com"),
                new User(4, "Diana Prince", "diana.prince@example.com"),
                new User(5, "Eve Adams", "eve.adams@example.com"),
                new User(6, "Frank Wright", "frank.wright@example.com"),
                new User(7, "Grace Hopper", "grace.hopper@example.com")
        );
    }
}