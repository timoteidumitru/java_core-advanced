package com.javaPlayground.algorithms.dataSorting;

import java.util.*;
import java.util.stream.Collectors;

public class Runner {
    public static void main(String[] args) {
        List<Employee> employees = new ArrayList<>(){{
            add(new Employee(1, "John Doe", 31, "New York", "HR", new PaymentDetails(75000, "Bank of England", 12345678, 112233)));
            add(new Employee(2, "Jane Smith", 28, "London", "IT", new PaymentDetails(60000, "Wells Fargo", 23456789, 223344)));
            add(new Employee(3, "Mike Johnson", 35, "Chicago", "Finance", new PaymentDetails(85000, "Chase", 34567890, 334455)));
            add(new Employee(4, "Emily Davis", 26, "London", "Transport", new PaymentDetails(72000, "Citibank", 45678901, 445566)));
            add(new Employee(5, "David Brown", 32, "Phoenix", "Construction", new PaymentDetails(75000, "PNC Bank", 56789012, 556677)));
            add(new Employee(6, "Susan Wilson", 28, "New York", "IT", new PaymentDetails(78000, "US Bank", 67890123, 667788)));
            add(new Employee(7, "Chris Moore", 40, "San Antonio", "Finance", new PaymentDetails(90000, "Citibank", 78901234, 778899)));
            add(new Employee(8, "Jessica Taylor", 31, "London", "Construction", new PaymentDetails(75000, "Barclays", 89012345, 889900)));
            add(new Employee(9, "Mark Anderson", 27, "Dallas", "IT", new PaymentDetails(60000, "Citibank", 90123456, 990011)));
            add(new Employee(10, "Sarah Lee", 33, "Chicago", "Construction", new PaymentDetails(90000, "Bank of England", 12345679, 001122)));
        }};

        //1. Filter data by departments
//        filterByDepartments(employees);
        //2. Filter data by salary
//        filterBySalary(employees);
        //3. Filter data by age
//        filterByAge(employees);
        //4. Group data by bank belongings
//        groupByBankName(employees);
        //5. Group data by location
//        groupByLocation(employees);
        //6. Group data by department and age
//        groupByAgeAndDepartment(employees);
        //7. Group data by department and salary
        groupBySalaryAndDepartment(employees);

    }

    public static void filterByDepartments(List<Employee> employees){
        Map<String, List<String>> collect = employees.stream().collect(Collectors.groupingBy(Employee::getDepartment,
                Collectors.mapping(Employee::getName, Collectors.toList())));
        System.out.println(collect);
    }

    public static void filterBySalary(List<Employee> employees){
        Map<Double, List<String>> collect = employees.stream().collect(Collectors
                .groupingBy(em -> em.getPaymentDetails().getSalary(),
                        Collectors.mapping(Employee::getName, Collectors.toList())));
        System.out.println(collect);
    }

    public static void filterByAge(List<Employee> employees){
        Map<Integer, List<String>> collect = employees.stream().collect(Collectors.groupingBy(Employee::getAge,
                Collectors.mapping(Employee::getName, Collectors.toList())));
        System.out.println(collect);
    }

    public static void groupByBankName(List<Employee> employees){
        Map<String, List<String>> collect = employees.stream().collect(Collectors.groupingBy(employee ->
                employee.getPaymentDetails().getBankName(), Collectors.mapping(Employee::getName, Collectors.toList())));
        System.out.println(collect);
    }

    public static void groupByLocation(List<Employee> employees){
        Map<String, List<String>> collect = employees.stream().collect(Collectors.groupingBy(Employee::getLocation,
                Collectors.mapping(Employee::getName, Collectors.toList())));
        System.out.println(collect);
    }

    private static void groupByAgeAndDepartment(List<Employee> employees) {
        Map<String, Map<String, Integer>> collect = employees.stream().collect(Collectors.groupingBy(Employee::getDepartment,
                Collectors.toMap(Employee::getName, Employee::getAge)));
        System.out.println(collect);
    }

    private static void groupBySalaryAndDepartment(List<Employee> employees) {
        Map<Double, Map<String, String>> collect = employees.stream().collect(Collectors.groupingBy(employee ->
                employee.getPaymentDetails().getSalary(), Collectors.toMap(Employee::getName, Employee::getDepartment)));
        System.out.println(collect);
    }

}
