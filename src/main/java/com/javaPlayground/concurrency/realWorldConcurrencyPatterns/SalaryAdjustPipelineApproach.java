package com.javaPlayground.concurrency.realWorldConcurrencyPatterns;

import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.*;

public class SalaryAdjustPipelineApproach {

    static record Employee(long id, String name, int age, double salary, String dept) {}

    static final BlockingQueue<Employee> ingestQ = new ArrayBlockingQueue<>(1_000);
    static final BlockingQueue<Employee> transformQ = new ArrayBlockingQueue<>(1_000);

    static final Employee POISON = new Employee(-1, "", 0, 0, "");

    public static void main(String[] args) throws Exception {

        long start = System.nanoTime();

        // ---------------- INGEST ----------------
        Thread ingest = new Thread(() -> {
            try {
                Files.lines(Path.of("src/main/resources/IO_operations/large_dataset.csv"))
                        .skip(1)
                        .map(SalaryAdjustPipelineApproach::parse)
                        .forEach(e -> {
                            try { ingestQ.put(e); }
                            catch (InterruptedException ignored) {}
                        });
                ingestQ.put(POISON);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // ---------------- TRANSFORM ----------------
        Thread transform = new Thread(() -> {
            try {
                while (true) {
                    Employee e = ingestQ.take();
                    if (e == POISON) {
                        transformQ.put(POISON);
                        break;
                    }
                    transformQ.put(adjustSalary(e));
                }
            } catch (InterruptedException ignored) {}
        });

        // ---------------- PERSIST ----------------
        Thread persist = new Thread(() -> {
            Path out = Path.of(
                    "src/main/resources/IO_operations/concurrency/pipeline.csv"
            );
            try (BufferedWriter w = Files.newBufferedWriter(out)) {
                w.write("ID,Name,Age,Salary,Department\n");
                while (true) {
                    Employee e = transformQ.take();
                    if (e == POISON) break;
                    w.write(e.id()+","+e.name()+","+e.age()+","+e.salary()+","+e.dept()+"\n");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        ingest.start();
        transform.start();
        persist.start();

        ingest.join();
        transform.join();
        persist.join();

        long timeMs = (System.nanoTime() - start) / 1_000_000;

        System.out.println("=========================================");
        System.out.println("Pipeline Pattern Salary Adjustment");
        System.out.println("Execution time : " + timeMs + " ms");
        System.out.println("=========================================");
    }

    static Employee parse(String l) {
        String[] t = l.split(",");
        return new Employee(
                Long.parseLong(t[0]), t[1],
                Integer.parseInt(t[2]),
                Double.parseDouble(t[3]),
                t[4]
        );
    }

    static Employee adjustSalary(Employee e) {
        double s = e.salary();
        if (s < 50_000) s *= 1.15;
        else if (s <= 75_000) s *= 1.05;
        else if (s <= 100_000) s *= 1.07;
        else s *= 1.10;
        return new Employee(e.id(), e.name(), e.age(), s, e.dept());
    }
}

