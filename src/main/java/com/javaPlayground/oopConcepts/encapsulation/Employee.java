package com.javaPlayground.oopConcepts.encapsulation;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Employee {

    private int id;
    private String name;
    private String department;
    private int age;
    private int salary;

    public Employee(int id, String name, String department, int age, int salary) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.age = age;
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "EmployeeDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", department='" + department + '\'' +
                ", age=" + age +
                ", salary=" + salary +
                '}';
    }
}
