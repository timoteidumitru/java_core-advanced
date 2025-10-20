package com.javaPlayground.functionalInterfaces;

public class Employee {
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

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
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

