package com.javaPlayground.functionalInterfaces;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Employee {
    // Getters and Setters
    private int id;
    private String name;
    private int age;
    private String department;

    // Constructor
    public Employee(int id, String name, int age, String department) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.department = department;
    }

    // toString method for easy printing
    @Override
    public String toString() {
        return "EmployeeDTO{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", department='" + department + '\'' +
                '}';
    }
}

