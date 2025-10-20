package com.javaPlayground.javaBatch.config;

import com.javaPlayground.javaBatch.dto.EmployeeDTO;
import com.javaPlayground.javaBatch.entity.Employee;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;

@Configuration
public class EmployeeProcessor implements ItemProcessor<EmployeeDTO, Employee> {

    @Override
    public Employee process(EmployeeDTO employeeDTO) throws Exception {
        Employee employee = new Employee();
        double salary = employeeDTO.getSalary();

        employee.setId(employeeDTO.getId());
        employee.setName(employeeDTO.getName());
        employee.setAge(employeeDTO.getAge());
        employee.setSalaryBefore(salary);
        employee.setDepartment(employeeDTO.getDepartment());

        if (salary > 100000) {
            salary *= 1.10; // Increase by 10%
        } else if (salary > 70000) {
            salary *= 1.15; // Increase by 15%
        } else {
            salary *= 1.25; // Increase by 25%
        }

        // Round salary to 2 decimal places
        salary = Math.round(salary * 100.0) / 100.0;

        employee.setSalaryAfter(salary);

        return employee;
    }
}
