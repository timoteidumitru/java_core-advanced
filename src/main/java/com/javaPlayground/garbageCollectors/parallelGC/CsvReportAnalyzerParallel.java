package com.javaPlayground.garbageCollectors.parallelGC;

import com.javaPlayground.garbageCollectors.Employee;

import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

public class CsvReportAnalyzerParallel {

    public static void main(String[] args) {
        System.out.println("=== Employee Data Analyzer (Parallel GC Demo) ===");

        String filePath = "src/main/resources/IO_operations/large_dataset.csv";
        List<Employee> employees = new ArrayList<>();

        // Step 1: Read and parse CSV
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int count = 0;
            reader.readLine(); // Skip header

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    int id = Integer.parseInt(parts[0].trim());
                    String name = parts[1].trim();
                    int age = Integer.parseInt(parts[2].trim());
                    double salary = Double.parseDouble(parts[3].trim());
                    String department = parts[4].trim();
                    employees.add(new Employee(id, name, age, salary, department));
                }

                // Simulate GC pressure
                if (++count % 2000 == 0) {
                    System.out.println("Processed " + count + " records...");
                    byte[] temp = new byte[1024 * 1024]; // 1MB temp object
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        // Step 2: Compute totals
        Map<String, Double> salaryByDept = new HashMap<>();
        for (Employee emp : employees) {
            salaryByDept.merge(emp.getDepartment(), emp.getSalary(), Double::sum);
        }

        Map<String, List<Integer>> agesByDept = new HashMap<>();
        for (Employee emp : employees) {
            agesByDept.computeIfAbsent(emp.getDepartment(), k -> new ArrayList<>()).add(emp.getAge());
        }

        Map<String, Double> avgAgeByDept = new HashMap<>();
        for (var entry : agesByDept.entrySet()) {
            List<Integer> ages = entry.getValue();
            avgAgeByDept.put(entry.getKey(), ages.stream().mapToInt(Integer::intValue).average().orElse(0.0));
        }

        // Step 3: Print results
        DecimalFormat formatter = new DecimalFormat("#,###.00");

        System.out.println("\n=== Summary by Department ===");
        System.out.printf("%-20s | %-20s | %-10s%n", "Department", "Total Salary", "Avg Age");
        System.out.println("------------------------------------------------------------------");

        for (String dept : salaryByDept.keySet()) {
            double totalSalary = salaryByDept.get(dept);
            String formattedSalary = formatter.format(totalSalary);
            double avgAge = avgAgeByDept.get(dept);

            System.out.printf("%-20s | $%-19s | %-10.1f%n", dept, formattedSalary, avgAge);
        }

        System.out.println("\nAnalysis complete. Processed " + employees.size() + " employees.");
    }
}
