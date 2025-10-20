package com.javaPlayground.comparableAndComparator;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

public class Runner {
    public static void main(String[] args) {
        List<Employee> firmX = getAllEmployee();
        firmX.sort(new SalaryComparator());

        Comparator<Employee> compareSalary = Comparator.comparing(Employee::getSalary);
        Comparator<Employee> compareAge = Comparator.comparing(Employee::getAge);

        Map<String, Optional<Employee>> sortedBySalary = firmX.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors
                        .reducing(BinaryOperator.maxBy(compareSalary))));

        Map<Integer, Long> countPeopleByAge = firmX.stream()
                .collect(Collectors.groupingBy(Employee::getAge,
                        Collectors.mapping(Employee::getAge, Collectors.counting())));

        Map<String, Map<String, Integer>> sortSalaryByDepartment = firmX.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.toMap(Employee::getName, Employee::getSalary)));

        Map<String, Map<String, Integer>> groupAgeAndDepartments = firmX.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment,
                Collectors.toMap(Employee::getName, Employee::getAge)));

        System.out.println(groupAgeAndDepartments);
    }

    public static List<Employee> getAllEmployee() {
        return new ArrayList<Employee>() {{
            add(new Employee(1, "Ana", 55000, 24, "HR"));
            add(new Employee(2, "Mike", 58000, 24, "HR"));
            add(new Employee(3, "Jane", 64000, 22, "Finance"));
            add(new Employee(4, "John", 74000, 28, "R&D"));
            add(new Employee(5, "Alex", 90000, 26, "IT"));
            add(new Employee(6, "Iov", 75000, 26, "IT"));
            add(new Employee(7, "Geo", 64000, 22, "Finance"));
            add(new Employee(8, "Maria", 70000, 24, "HR"));
            add(new Employee(9, "Pavel", 60000, 28, "R&D"));
            add(new Employee(10, "Tim", 64000, 28, "Finance"));
            add(new Employee(11, "Ben", 75000, 28, "IT"));
            add(new Employee(12, "Sam", 85000, 28, "IT"));
        }};
    }

}
