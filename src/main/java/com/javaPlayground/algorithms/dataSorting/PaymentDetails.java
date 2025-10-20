package com.javaPlayground.algorithms.dataSorting;

import java.util.Objects;

public class PaymentDetails {
    private double salary;
    private String bankName;
    private int account;
    private int sortCode;

    public PaymentDetails(double salary, String bankName, int account, int sortCode) {
        this.salary = salary;
        this.bankName = bankName;
        this.account = account;
        this.sortCode = sortCode;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public int getAccount() {
        return account;
    }

    public void setAccount(int account) {
        this.account = account;
    }

    public int getSortCode() {
        return sortCode;
    }

    public void setSortCode(int sortCode) {
        this.sortCode = sortCode;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        PaymentDetails that = (PaymentDetails) object;
        return Double.compare(salary, that.salary) == 0 && account == that.account && sortCode == that.sortCode && Objects.equals(bankName, that.bankName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(salary, bankName, account, sortCode);
    }

    @Override
    public String toString() {
        return "PaymentDetails{" +
                "salary=" + salary +
                ", bankName='" + bankName + '\'' +
                ", account=" + account +
                ", sortCode=" + sortCode +
                '}';
    }
}
