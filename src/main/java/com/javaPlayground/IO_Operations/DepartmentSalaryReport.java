package com.javaPlayground.IO_Operations;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class DepartmentSalaryReport {

    public static void main(String[] args) {
        String inputFile = "src/main/resources/IO_operations/large_dataset.csv";                // Input CSV file
        String outputFile = "src/main/resources/IO_operations/report/department_report.csv";    // Output report file

        // Maps to hold department -> (total salary, count)
        Map<String, Double> totalSalaries = new HashMap<>();
        Map<String, Integer> departmentCounts = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;
            boolean headerSkipped = false;

            while ((line = reader.readLine()) != null) {
                if (!headerSkipped) {
                    headerSkipped = true;
                    continue; // skip the header
                }

                String[] parts = line.split(",");
                if (parts.length < 5) continue; // skip malformed lines

                String department = parts[4].trim();
                double salary;

                try {
                    salary = Double.parseDouble(parts[3]);
                } catch (NumberFormatException e) {
                    continue; // skip lines with invalid salary
                }

                // Accumulate salary and count for each department
                totalSalaries.merge(department, salary, Double::sum);
                departmentCounts.merge(department, 1, Integer::sum);
            }

        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // Compute averages and write to output file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            writer.write("Department,AverageSalary");
            writer.newLine();

            for (String dept : totalSalaries.keySet()) {
                double total = totalSalaries.get(dept);
                int count = departmentCounts.get(dept);
                double average = total / count;
                writer.write(dept + "," + String.format("%.2f", average));
                writer.newLine();
            }

            System.out.println("Report generated successfully: department_report.csv");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

