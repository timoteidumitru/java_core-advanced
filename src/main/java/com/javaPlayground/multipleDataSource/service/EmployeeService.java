package com.javaPlayground.multipleDataSource.service;

import com.javaPlayground.multipleDataSource.model.employee.Employee;
import com.javaPlayground.multipleDataSource.repository.employee.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees(){
         return  employeeRepository.findAll();
    }

    public Employee addEmployee(Employee employee){
        employeeRepository.save(employee);
        return employee;
    }
}
