package com.javaPlayground.multipleDataSource.repository.employee;

import com.javaPlayground.multipleDataSource.model.employee.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
