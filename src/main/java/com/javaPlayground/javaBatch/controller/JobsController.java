package com.javaPlayground.javaBatch.controller;

import com.javaPlayground.javaBatch.entity.Employee;
import com.javaPlayground.javaBatch.repository.EmployeeRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.zip.GZIPOutputStream;


@RestController
@RequestMapping("/jobs")
public class JobsController {

    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private Job job;
    @Autowired
    EmployeeRepository employeeRepository;

//    @PostMapping("/import-customers")
//    public List<Customer> importCustomersToDB(){
//        JobParameters jobParameters = new JobParametersBuilder()
//                .addLocalDate("startAt: ", LocalDate.now())
//                .toJobParameters();
//
//        try {
//            jobLauncher.run(job, jobParameters);
//        } catch (JobExecutionAlreadyRunningException | JobParametersInvalidException |
//                 JobInstanceAlreadyCompleteException | JobRestartException e) {
//            throw new RuntimeException(e);
//        }
//    }

    @PostMapping("/import-employee")
    public void importEmployeeToDB(HttpServletResponse response) {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLocalDate("startAt: ", LocalDate.now())
                .toJobParameters();

        try {
            // Run the Spring Batch job
            jobLauncher.run(job, jobParameters);

            // Set response headers for GZIP compression
            response.setContentType("application/json");
            response.setHeader("Content-Encoding", "gzip");

            // Fetch manipulated employee data and compress it
            try (OutputStream os = response.getOutputStream();
                 GZIPOutputStream gzipOutputStream = new GZIPOutputStream(os)) {
                List<Employee> employees = employeeRepository.findAll();

                // Convert to JSON and write to GZIP output stream
                String json = convertToJson(employees);
                gzipOutputStream.write(json.getBytes());
            }
        } catch (JobExecutionAlreadyRunningException | JobParametersInvalidException |
                 JobInstanceAlreadyCompleteException | JobRestartException | IOException e) {
            throw new RuntimeException("Error while processing and compressing employee data", e);
        }
    }

    /**
     * Converts a list of Employee objects to a JSON string.
     * Replace this with Jackson or Gson for a production-ready implementation.
     */
    private String convertToJson(List<Employee> employees) {
        StringBuilder json = new StringBuilder();
        json.append("[");
        for (int i = 0; i < employees.size(); i++) {
            Employee emp = employees.get(i);
            json.append("{")
                    .append("\"id\":").append(emp.getId()).append(",")
                    .append("\"name\":\"").append(emp.getName()).append("\",")
                    .append("\"age\":").append(emp.getAge()).append(",")
                    .append("\"salaryBefore\":").append(emp.getSalaryBefore()).append(",")
                    .append("\"salaryAfter\":").append(emp.getSalaryAfter()).append(",")
                    .append("\"department\":\"").append(emp.getDepartment()).append("\"")
                    .append("}");
            if (i < employees.size() - 1) {
                json.append(",");
            }
        }
        json.append("]");
        return json.toString();
    }

}


// ############################ For 1000 entries dataset ##################################
//   Runs without executors  ||    Runs with executors        ||   Runs with partitioner(for 100k dataset)
// - time: 2.92s             || - time: 1.93s                 || - time: 886ms (2 grid)
// - time: 1.82s             || - time: 1.40s                 || - time: 760ms (4 grid)
// - time: 1.84s             || - time: 1.37s                 || - time: 800ms (10 grid)
// - time: 1.93s             || - time: 1.32s                 || - time: 768ms (5 grid)
// - time: 1.82s(1.66s)      || - time: 1.33s(1.18s)          || - time: 817ms (16 grid)

// ################################# For 100k dataset ##################################
//         Runs without partitioning        ||     Runs with partitioning
//              - time: 14.78s              ||      - time: 9.23s @9.77MB(return data to client)
//              - time: 17.77s              ||      - time: 7.76s (just execution part)
//                                          ||      - time: 52.44s(with @StepScope, avoiding thread starvation)
//                                          ||      - time: 54.32s(added gzip and compress the return from 16.87MB to 2.61MB)