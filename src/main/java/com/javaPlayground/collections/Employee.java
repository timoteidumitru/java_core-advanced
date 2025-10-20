package com.javaPlayground.collections;

import java.util.Objects;

public class Employee implements Comparable<Employee>{
    int id;
    String name;
    int salary;
    int age;

    String department;

    public Employee(int id, String name, int salary, int age, String department) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.age = age;
        this.department = department;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getSalary() {
        return salary;
    }

    public int getAge() {
        return age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return id == employee.id && salary == employee.salary && age == employee.age && Objects.equals(name, employee.name) && Objects.equals(department, employee.department);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, salary, age, department);
    }

    @Override
    public String toString() {
        return "EmployeeDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                ", age=" + age +
                ", department='" + department + '\'' +
                '}';
    }

    @Override
    public int compareTo(Employee e) {
        if (id == e.id){
            return 0;
        } else if (id > e.id) {
            return 1;
        } else {
            return -1;
        }
    }
}
