package com.javaPlayground.algorithms.dataSorting;

public class Employee {
    private int id;
    private String name;
    private int age;
    private String location;
    private String department;
    private PaymentDetails paymentDetails;

    public Employee(int id, String name, int age, String location, String department, PaymentDetails paymentDetails) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.location = location;
        this.paymentDetails = paymentDetails;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public PaymentDetails getPaymentDetails() {
        return paymentDetails;
    }

    public void setPaymentDetails(PaymentDetails paymentDetails) {
        this.paymentDetails = paymentDetails;
    }

    @Override
    public String toString() {
        return "\nEmployeeDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", location='" + location + '\'' +
                ", department='" + department + '\'' +
                ", paymentDetails=" + paymentDetails +
                '}';
    }
}
