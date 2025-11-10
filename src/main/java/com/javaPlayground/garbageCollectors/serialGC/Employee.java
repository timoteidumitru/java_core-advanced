package com.javaPlayground.garbageCollectors.serialGC;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    private int id;
    private String name;
    private int age;
    private double salary;
    private String department;

    @Override
    public String toString() {
        return String.format("%d,%s,%d,%.2f,%s", id, name, age, salary, department);
    }
}
