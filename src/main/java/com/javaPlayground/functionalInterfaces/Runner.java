package com.javaPlayground.functionalInterfaces;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Runner {
    public static void main(String[] args) {
        dollarsToEuroConvertor(); // Function Interface accepts input and returns a result.
//        retirementEligibility(); // Predicate evaluates input and returns true or false.
//        notificationSender(); // Consumer Interface performs action on input without returning result.
//        idGenerator(); // Supplier Interface provides output without requiring any input.
    }

    // Example of Supplier Interface
    public static void idGenerator(){
        Supplier<String> generateOrderId = () -> UUID.randomUUID().toString();
        String newOrderId = generateOrderId.get();
        System.out.println("UUId generated: " + newOrderId);
    }

    // Example of Consumer Interface
    public static void notificationSender(){
        EmailService emailService = new EmailService();
        Consumer<User> sendNotification = user -> emailService.send(user.getEmail(),
                "Notification Subject",
                "Hey! Just want to remind you of..");

        List<User> users = getUsers();
        users.forEach(sendNotification);

    }

    // Example of Predicate Interface
    public static void retirementEligibility(){
        List<Employee> employees = getEmployees();
        Predicate<Employee> isEligibleForRetirement = employee -> employee.getAge() >= 55;
        List<Employee> eligibleEmployees = employees.stream()
                .filter(isEligibleForRetirement)
                .toList();
        System.out.println("Employees that are 55 and over that are eligible for retirement: \n");
        eligibleEmployees.forEach(System.out::println);
    }

    // Example of Function Interface
    public static void dollarsToEuroConvertor() {
        Function<Double, Double> convertToEuros = priceInDollars -> priceInDollars * 0.85;
        List<Double> pricesInDollars = Arrays.asList(100.0, 200.0, 50.0);
        List<Double> pricesInEuros = pricesInDollars.stream()
                .map(convertToEuros)
                .toList();

        System.out.println("Conversion from $ to € for the following input(dollars): " + pricesInDollars);
        for (int i = 0; i < pricesInDollars.size(); i++) {
            System.out.println(
                    "$" + pricesInDollars.get(i) + " converted to €" + pricesInEuros.get(i));
        }
    }

    private static List<Employee> getEmployees() {
        Employee emp1 = new Employee(1, "John Doe", 60, "Finance");
        Employee emp2 = new Employee(2, "Jane Smith", 45, "Marketing");
        Employee emp3 = new Employee(3, "Mke Brown", 55, "IT");
        Employee emp4 = new Employee(4, "Joseph Smith", 35, "Accounting");
        Employee emp5 = new Employee(5, "James Cross", 44, "Construction");
        Employee emp6 = new Employee(6, "Michael Allan", 50, "Transporting");
        Employee emp7 = new Employee(7, "Allan McAllister", 64, "HR");

        return Arrays.asList(emp1, emp2, emp3, emp4, emp5, emp6, emp7);
    }

    private static List<User> getUsers() {
        User user1 = new User(1, "Alice Johnson", "alice.johnson@example.com");
        User user2 = new User(2, "Bob Smith", "bob.smith@example.com");
        User user3 = new User(3, "Charlie Brown", "charlie.brown@example.com");
        User user4 = new User(4, "Diana Prince", "diana.prince@example.com");
        User user5 = new User(5, "Eve Adams", "eve.adams@example.com");
        User user6 = new User(6, "Frank Wright", "frank.wright@example.com");
        User user7 = new User(7, "Grace Hopper", "grace.hopper@example.com");

        return Arrays.asList(user1, user2, user3, user4, user5, user6, user7);
    }

}
