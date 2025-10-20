package com.javaPlayground.javaBatch.repository;

import com.javaPlayground.javaBatch.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
}
