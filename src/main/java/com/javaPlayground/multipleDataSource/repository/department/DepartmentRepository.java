package com.javaPlayground.multipleDataSource.repository.department;

import com.javaPlayground.multipleDataSource.model.department.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

}
