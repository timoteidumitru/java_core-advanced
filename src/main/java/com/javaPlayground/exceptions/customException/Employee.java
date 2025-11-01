package com.javaPlayground.exceptions.customException;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

    private int id;
    private String name;
    private String location;
    private String department;
    private int salary;

}
