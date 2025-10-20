package com.javaPlayground.multipleDataSource.controller;

import com.javaPlayground.multipleDataSource.model.employee.Employee;
import com.javaPlayground.multipleDataSource.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    @GetMapping("/employees")
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @PostMapping("/add-employee")
    public ResponseEntity<?> addEmployee(@RequestBody Employee employee) {
        try {
            Employee savedEmployee = employeeService.addEmployee(employee);
            return ResponseEntity.ok(savedEmployee);
        } catch (Exception ex) {
            String errorMessage = "Something went wrong when saving employee to the database: " + ex.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }

}
