package com.javaPlayground.collections;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Runner {
    public static void main(String[] args) {
        List<Employee> firmX = getAllEmployee();

        // find the employee with the highest salary from each department
        findHighestSalary(firmX);
    }

    private static void findHighestSalary(List<Employee> firmX) {
//        // approach one
//        Map<String, EmployeeDTO> collect = firmX.stream().collect(Collectors
//                .toMap(EmployeeDTO::getDepartment, Function.identity(), BinaryOperator.maxBy(Comparator
//                        .comparingInt(EmployeeDTO::getSalary))));
//
//        collect.forEach((department, employee) ->
//                System.out.println("Department: " + department + ", " +
//                    "Name: " + employee.getName() + ", Salary: $" + employee.getSalary()));

        // approach two
        Map<String, Employee> collect1 = firmX.stream().collect(Collectors
                .groupingBy(Employee::getDepartment, Collectors.collectingAndThen(Collectors
                        .maxBy(Comparator.comparingDouble(Employee::getSalary)), Optional::get)));
        collect1.forEach((department, employee) ->
                System.out.println("Department: " + department + ", " +
                        "Name: " + employee.getName() + ", Salary: $" + employee.getSalary()));
    }


    public static List<Employee> getAllEmployee() {
        return new ArrayList<>() {{
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
