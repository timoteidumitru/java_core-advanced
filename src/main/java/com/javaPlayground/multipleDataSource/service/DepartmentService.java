package com.javaPlayground.multipleDataSource.service;

import com.javaPlayground.multipleDataSource.model.department.Department;
import com.javaPlayground.multipleDataSource.repository.department.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {
    @Autowired
    DepartmentRepository departmentRepository;

    public List<Department> getAllDepartments(){
        return departmentRepository.findAll();
    }

    public Department addDepartment(Department department){
        departmentRepository.save(department);
        return department;
    }
}
