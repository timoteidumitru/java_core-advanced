package com.javaPlayground.javaBatch.config;

import com.javaPlayground.javaBatch.entity.Employee;
import com.javaPlayground.javaBatch.repository.EmployeeRepository;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;


@Component
public class EmployeeWriter implements ItemWriter<Employee> {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public void write(Chunk<? extends Employee> chunk) throws Exception {
        employeeRepository.saveAll(chunk.getItems());
    }

}
