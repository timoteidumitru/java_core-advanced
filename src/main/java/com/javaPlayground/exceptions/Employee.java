package com.javaPlayground.exceptions;

import java.util.Objects;

public class Employee {
    private int id;
    private String name;
    private String location;
    private String department;
    private int salary;

    public Employee(int id, String name, String location, String department, int salary) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.department = department;
        this.salary = salary;
    }

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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "EmployeeDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", department='" + department + '\'' +
                ", salary=" + salary +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Employee employee = (Employee) object;
        return id == employee.id && salary == employee.salary && Objects.equals(name, employee.name) && Objects.equals(location, employee.location) && Objects.equals(department, employee.department);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, location, department, salary);
    }
}
