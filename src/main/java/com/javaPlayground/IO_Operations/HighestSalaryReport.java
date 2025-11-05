package com.javaPlayground.IO_Operations;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class HighestSalaryReport {

    public static void main(String[] args) {
        String inputFile = "src/main/resources/IO_operations/large_dataset.csv";                     // Input CSV file
        String outputFile = "src/main/resources/IO_operations/report/highest_salary_report.csv";     // Output report file

        // Map to hold department -> the highest salary
        Map<String, Double> highestSalaries = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;
            boolean headerSkipped = false;

            while ((line = reader.readLine()) != null) {
                if (!headerSkipped) {
                    headerSkipped = true;
                    continue; // skip header line
                }

                String[] parts = line.split(",");
                if (parts.length < 5) continue; // skip malformed lines

                String department = parts[4].trim();
                double salary;

                try {
                    salary = Double.parseDouble(parts[3]);
                } catch (NumberFormatException e) {
                    continue; // skip invalid salary lines
                }

                // Update the highest salary for this department
                highestSalaries.merge(department, salary, Math::max);
            }

        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // Write the report file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            writer.write("Department,HighestSalary");
            writer.newLine();

            for (String dept : highestSalaries.keySet()) {
                double highest = highestSalaries.get(dept);
                writer.write(dept + "," + String.format("%.2f", highest));
                writer.newLine();
            }

            System.out.println("Report generated successfully: highest_salary_report.csv");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
