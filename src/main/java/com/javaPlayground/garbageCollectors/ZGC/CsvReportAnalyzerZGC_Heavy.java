package com.javaPlayground.garbageCollectors.ZGC;

import com.javaPlayground.garbageCollectors.Employee;

import java.io.*;
import java.util.*;

public class CsvReportAnalyzerZGC_Heavy {

    public static void main(String[] args) {
        System.out.println("=== 🚀 Employee Data Analyzer (ZGC Heavy Load Showcase) ===\n");

        String filePath = "src/main/resources/IO_operations/large_dataset.csv";
        List<Employee> employees = new ArrayList<>(20_000_000);

        // Step 1: Load CSV file
        System.out.println("[1️⃣] Reading base CSV file (100k entries)...");
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            reader.readLine(); // skip header
            int count = 0;

            while ((line = reader.readLine()) != null && count < 100_000) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    employees.add(new Employee(
                            Integer.parseInt(parts[0].trim()),
                            parts[1].trim(),
                            Integer.parseInt(parts[2].trim()),
                            Double.parseDouble(parts[3].trim()),
                            parts[4].trim()
                    ));
                }
                count++;
            }
        } catch (IOException e) {
            System.out.println("❌ Error reading file: " + e.getMessage());
        }

        printMemory("After initial dataset load (100K employees)");

        // Step 2: Expand dataset to simulate large-scale processing
        System.out.println("[2️⃣] Expanding dataset to 50 million records to simulate enterprise-scale analytics...");
        while (employees.size() < 50_000_000) {
            employees.addAll(new ArrayList<>(employees));
        }
        employees = employees.subList(0, 50_000_000);

        printMemory("After expanding to 50M employees");

        // Step 3: Simulate analytical workloads
        System.out.println("[3️⃣] Starting heavy computations — calculating aggregates across departments...");

        Map<String, Double> salaryByDept = new HashMap<>();
        Map<String, Employee> oldestByDept = new HashMap<>();
        Random random = new Random();
        List<byte[]> tempData = new ArrayList<>();

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < employees.size(); i++) {
            Employee emp = employees.get(i);

            // Aggregate salary by department
            salaryByDept.merge(emp.getDepartment(), emp.getSalary(), Double::sum);

            // Track the oldest employee
            oldestByDept.merge(emp.getDepartment(), emp,
                    (e1, e2) -> e1.getAge() > e2.getAge() ? e1 : e2);

            // Heavy allocation every 100k iterations (simulating transient workloads)
            if (i % 100_000 == 0) {
                tempData.add(new byte[10 * 1024 * 1024]); // 10 MB block
            }

            // Periodic logging for memory progression
            if (i % 5_000_000 == 0 && i > 0) {
                System.out.printf("   🔹 Processed %,d employees... (GC load active)%n", i);
                printMemory("During computation");
            }
        }

        long duration = (System.currentTimeMillis() - startTime) / 1000;
        printMemory("After all computations completed");

        // Step 4: Output minimal summary
        System.out.println("\n✅ [Summary]");
        System.out.println("   • Departments analyzed: " + salaryByDept.size());
        System.out.println("   • Total employees processed: " + employees.size());
        System.out.println("   • Runtime: " + duration + " seconds");
        System.out.println("   • GC logs available in: gc-perf.log");
        System.out.println("\n🧠 Note: Observe that ZGC maintains microsecond pause times even under >15GB heap load.\n");
    }

    private static void printMemory(String stage) {
        Runtime runtime = Runtime.getRuntime();
        long used = (runtime.totalMemory() - runtime.freeMemory()) / (1024 * 1024);
        long total = runtime.totalMemory() / (1024 * 1024);
        long max = runtime.maxMemory() / (1024 * 1024);
        System.out.printf("[Memory] %-45s | Used: %,8d MB | Total: %,8d MB | Max: %,8d MB%n",
                stage, used, total, max);
    }
}
