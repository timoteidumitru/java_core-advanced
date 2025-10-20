package com.javaPlayground.oopConcepts.encapsulation;

import java.util.ArrayList;
import java.util.List;

public class Runner {
    /*
        - Bind all fields as a single entry
        - Define all attributes as private
        - Define all public exposed methods(getters/setters)
        Use case:
            - In Entity and DTO classes(eg EmployeeDTO class).
    */
    public static void main(String[] args) {
        List<Employee> employees = getAllEmployee();
        employees.forEach(System.out::println);
    }

    public static List<Employee> getAllEmployee() {
        return new ArrayList<>() {{
            add(new Employee(1, "Mike", "IT", 28, 55000));
            add(new Employee(2, "John", "HR", 22, 45000));
            add(new Employee(3, "Jane", "Marketing", 24, 48000));
            add(new Employee(4, "Ben", "Finance", 28, 58000));
            add(new Employee(5, "Tim", "IT", 34, 55000));
            add(new Employee(6, "Sam", "Finance", 41, 75000));
            add(new Employee(7, "Joseph", "HR", 39, 60000));
            add(new Employee(8, "Emily", "Marketing", 32, 42000));
            add(new Employee(9, "Daniela", "IT", 22, 40000));
            add(new Employee(10, "David", "Finance", 34, 45000));
        }};
    }
}
