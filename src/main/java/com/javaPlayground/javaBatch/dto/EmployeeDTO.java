package com.javaPlayground.javaBatch.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {
    private int id;
    private String name;
    private Integer age;
    private double salary;
    private String department;
}
