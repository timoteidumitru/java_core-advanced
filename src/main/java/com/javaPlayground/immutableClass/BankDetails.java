package com.javaPlayground.immutableClass;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BankDetails {
    private String bankName;
    private int sortCode;
    private int account;

    @Override
    public String toString() {
        return "BankDetails{" +
                "bankName='" + bankName + '\'' +
                ", sortCode=" + sortCode +
                ", account=" + account +
                '}';
    }
}
