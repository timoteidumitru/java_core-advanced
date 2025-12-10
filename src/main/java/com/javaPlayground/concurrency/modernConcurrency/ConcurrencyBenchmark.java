package com.javaPlayground.concurrency.modernConcurrency;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ConcurrencyBenchmark {

    // Number of concurrent I/O tasks
    private static final int TASK_COUNT = 500_000;

    // I/O simulation time (ms)
    private static final int IO_DELAY = 5;

    public static void main(String[] args) throws Exception {
        System.out.println("\n========= JAVA CONCURRENCY BENCHMARK =========");
        System.out.println("Tasks           : " + TASK_COUNT);
        System.out.println("Simulated I/O   : " + IO_DELAY + "ms");
        System.out.println("Java Version    : " + Runtime.version() + "\n");

        benchmark("Fixed Thread Pool (200 threads)",
                Executors.newFixedThreadPool(200));

        benchmark("Cached Thread Pool (elastic, platform threads)",
                Executors.newCachedThreadPool());

        benchmark("Virtual Thread Per Task (Loom)",
                Executors.newVirtualThreadPerTaskExecutor());

        benchmarkStructuredConcurrency();
    }

    // Generic benchmark runner
    private static void benchmark(String label, ExecutorService executor) throws Exception {
        System.out.println("---- " + label + " ----");

        Instant start = Instant.now();

        List<Callable<Void>> tasks = new ArrayList<>(TASK_COUNT);
        for (int i = 0; i < TASK_COUNT; i++) {
            tasks.add(() -> {
                simulateIO();
                return null;
            });
        }

        executor.invokeAll(tasks);
        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.MINUTES);

        Instant end = Instant.now();
        long ms = Duration.between(start, end).toMillis();

        printStats(ms);
    }

    // Structured concurrency example
    private static void benchmarkStructuredConcurrency() throws Exception {
        System.out.println("---- Structured Concurrency (Java 21) ----");

        Instant start = Instant.now();

        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            for (int i = 0; i < TASK_COUNT; i++) {
                scope.fork(() -> {
                    simulateIO();
                    return null;
                });
            }
            scope.join();
        }

        Instant end = Instant.now();
        long ms = Duration.between(start, end).toMillis();

        printStats(ms);
    }

    // Simulates blocking I/O work (DB call, HTTP call, file read)
    private static void simulateIO() {
        try {
            Thread.sleep(IO_DELAY);
        } catch (InterruptedException ignored) {}
    }

    // Output performance data
    private static void printStats(long milliseconds) {
        double seconds = milliseconds / 1000.0;
        double throughput = TASK_COUNT / seconds;

        System.out.println("Time: " + milliseconds + " ms");
        System.out.println("Throughput: " + (int)throughput + " tasks/sec");
        System.out.println("Approx worker threads used: " + Thread.activeCount());
        System.out.println();
    }
}

