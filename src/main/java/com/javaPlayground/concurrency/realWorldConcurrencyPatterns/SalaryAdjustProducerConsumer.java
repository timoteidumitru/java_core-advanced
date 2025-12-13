package com.javaPlayground.concurrency.realWorldConcurrencyPatterns;

import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.*;

public class SalaryAdjustProducerConsumer {

    static record Employee(long id, String name, int age, double salary, String dept) {}

    static final BlockingQueue<Employee> inputQueue = new ArrayBlockingQueue<>(1_000);
    static final BlockingQueue<Employee> outputQueue = new ArrayBlockingQueue<>(1_000);

    static final Employee POISON = new Employee(-1, "", 0, 0, "");

    public static void main(String[] args) throws Exception {

        int workers = Runtime.getRuntime().availableProcessors();

        ExecutorService consumers = Executors.newFixedThreadPool(workers);

        long startTime = System.nanoTime();

        // -------------------------
        // Writer (START FIRST)
        // -------------------------
        Thread writerThread = new Thread(() -> {
            Path outputPath = Path.of(
                    "src/main/resources/IO_operations/concurrency/producer-consumer.csv"
            );

            try (BufferedWriter writer = Files.newBufferedWriter(outputPath)) {

                writer.write("ID,Name,Age,Salary,Department\n");

                int poisonSeen = 0;
                while (poisonSeen < workers) {
                    Employee e = outputQueue.take();
                    if (e == POISON) {
                        poisonSeen++;
                    } else {
                        writer.write(
                                e.id() + "," +
                                        e.name() + "," +
                                        e.age() + "," +
                                        String.format("%.2f", e.salary()) + "," +
                                        e.dept() + "\n"
                        );
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        writerThread.start();

        // -------------------------
        // Consumers
        // -------------------------
        for (int i = 0; i < workers; i++) {
            consumers.submit(() -> {
                try {
                    while (true) {
                        Employee e = inputQueue.take();
                        if (e == POISON) {
                            outputQueue.put(POISON);
                            break;
                        }
                        outputQueue.put(adjustSalary(e));
                    }
                } catch (InterruptedException ignored) {}
            });
        }

        // -------------------------
        // Producer
        // -------------------------
        Files.lines(Path.of("src/main/resources/IO_operations/large_dataset.csv"))
                .skip(1)
                .map(SalaryAdjustProducerConsumer::parse)
                .forEach(e -> {
                    try {
                        inputQueue.put(e);
                    } catch (InterruptedException ignored) {}
                });

        // Stop consumers
        for (int i = 0; i < workers; i++) inputQueue.put(POISON);

        consumers.shutdown();
        consumers.awaitTermination(1, TimeUnit.MINUTES);

        writerThread.join();

        long endTime = System.nanoTime();

        long elapsedMs = (endTime - startTime) / 1_000_000;

        System.out.println("=========================================");
        System.out.println("Producer–Consumer Salary Adjustment");
        System.out.println("Threads used     : " + workers);
        System.out.println("Execution time   : " + elapsedMs + " ms");
        System.out.println("=========================================");
    }

    static Employee parse(String l) {
        String[] t = l.split(",");
        return new Employee(
                Long.parseLong(t[0]),
                t[1],
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
