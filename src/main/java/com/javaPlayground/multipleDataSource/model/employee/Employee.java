package com.javaPlayground.multipleDataSource.model.employee;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    @Id
    long id;
    String firstName;
    String lastName;
    String location;
    int age;
}
