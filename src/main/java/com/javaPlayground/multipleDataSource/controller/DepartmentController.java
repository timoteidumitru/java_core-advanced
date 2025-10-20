package com.javaPlayground.multipleDataSource.controller;

import com.javaPlayground.multipleDataSource.model.department.Department;
import com.javaPlayground.multipleDataSource.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DepartmentController {
    @Autowired
    DepartmentService departmentService;

    @GetMapping("/departments")
    public List<Department> getAllDepartments(){
        return departmentService.getAllDepartments();
    }

    @PostMapping("/add-department")
    public ResponseEntity<?> addDepartment(@RequestBody Department department) {
        try {
            Department savedDepartment = departmentService.addDepartment(department);
            return ResponseEntity.ok(savedDepartment);
        } catch (Exception ex) {
            String errorMessage = "Something went wrong when saving department to the database: " + ex.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }

}
