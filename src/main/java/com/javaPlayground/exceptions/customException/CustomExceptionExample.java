package com.javaPlayground.exceptions.customException;

import java.util.ArrayList;
import java.util.List;

public class CustomExceptionExample {
    public static void main(String[] args) throws EmployeeNotFoundException {

        Employee employee1 = new Employee(1, "Mike", "London", "Construction", 50000);
        Employee employee2 = new Employee(2, "John", "Germany", "IT", 60000);
        Employee employee3 = new Employee(3, "Jane", "New York", "HR", 50000);
        Employee employee4 = new Employee(4, "Doe", "Romania", "Finance", 60000);

        List<Employee> employee = new ArrayList<>();
        employee.add(employee1);
        employee.add(employee2);
        employee.add(employee3);
        employee.add(employee4);

        System.out.println(findById(employee, 2));

    }
    public static Employee findById(List<Employee> employees, int id) throws EmployeeNotFoundException {
        for (Employee employee : employees) {
            if (employee.getId() == id) {
                return employee;
            }
        }

        throw new EmployeeNotFoundException("Employee not found in DB");
    }
}
