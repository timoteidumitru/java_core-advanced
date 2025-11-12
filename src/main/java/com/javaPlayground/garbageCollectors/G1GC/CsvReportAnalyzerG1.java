package com.javaPlayground.garbageCollectors.G1GC;

import com.javaPlayground.garbageCollectors.Employee;

import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

public class CsvReportAnalyzerG1 {

    public static void main(String[] args) {
        System.out.println("=== Employee Data Analyzer (G1 GC Demo) ===");

        String filePath = "src/main/resources/IO_operations/large_dataset.csv";
        List<Employee> employees = new ArrayList<>();

        // Step 1: Read and parse CSV
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int count = 0;

            // Skip header
            reader.readLine();

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

                // Simulate memory churn for G1 GC
                if (++count % 2000 == 0) {
                    System.out.println("Processed " + count + " records...");
                    simulateMemoryLoad();
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        // Step 2: Compute total salary per department
        Map<String, Double> salaryByDept = new HashMap<>();
        for (Employee emp : employees) {
            salaryByDept.merge(emp.getDepartment(), emp.getSalary(), Double::sum);
        }

        // Step 3: Compute average age per department
        Map<String, List<Integer>> agesByDept = new HashMap<>();
        for (Employee emp : employees) {
            agesByDept.computeIfAbsent(emp.getDepartment(), k -> new ArrayList<>()).add(emp.getAge());
        }

        Map<String, Double> avgAgeByDept = new HashMap<>();
        for (var entry : agesByDept.entrySet()) {
            List<Integer> ages = entry.getValue();
            avgAgeByDept.put(entry.getKey(), ages.stream().mapToInt(Integer::intValue).average().orElse(0.0));
        }

        // Step 4: Print summary with formatting
        DecimalFormat formatter = new DecimalFormat("#,###.00");
        System.out.println("\n                === Analyzer Summary ===\n");
        System.out.printf("%-20s | %-20s | %-10s%n", "Department", "Total Salary", "Avg Age");
        System.out.println("------------------------------------------------------------------");

        for (String dept : salaryByDept.keySet()) {
            double totalSalary = salaryByDept.get(dept);
            double avgAge = avgAgeByDept.get(dept);
            String formattedSalary = formatter.format(totalSalary);

            System.out.printf("%-20s | $%-19s | %-10.1f%n", dept, formattedSalary, avgAge);
        }

        System.out.println("\nAnalysis complete. Processed " + employees.size() + " employees.");
        printMemoryStats();
    }

    private static void simulateMemoryLoad() {
        List<byte[]> junk = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            junk.add(new byte[1024 * 1024]); // 1 MB blocks
        }
        junk.clear(); // Make eligible for GC
    }

    private static void printMemoryStats() {
        Runtime runtime = Runtime.getRuntime();
        long used = (runtime.totalMemory() - runtime.freeMemory()) / (1024 * 1024);
        long total = runtime.totalMemory() / (1024 * 1024);
        System.out.println("\nMemory Usage After Analysis:");
        System.out.println("Used: " + used + " MB / Total: " + total + " MB");
    }
}
