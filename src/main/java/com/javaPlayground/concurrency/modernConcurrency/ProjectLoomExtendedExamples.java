package com.javaPlayground.concurrency.modernConcurrency;

import java.time.Instant;
import java.util.concurrent.*;
import java.util.*;

public class ProjectLoomExtendedExamples {

    public static void main(String[] args) throws Exception {

        System.out.println("=== Project Loom — Stage 10 Practical Examples ===\n");

        virtualThreadBasics();
        pinningExample();
        virtualThreadPerRequestDemo();
        structuredConcurrencyFailure();
        structuredConcurrencySuccess();
        structuredConcurrencyDeadline();
        replacingReactorSimulation();

        System.out.println("\n=== END OF EXAMPLES ===");
    }

    // ------------------------------------------------------------
    // A1. Virtual Threads Basics
    // ------------------------------------------------------------
    static void virtualThreadBasics() throws Exception {
        System.out.println("\n[1] Virtual Thread Basics — Millions of lightweight tasks\n");

        ExecutorService exec = Executors.newVirtualThreadPerTaskExecutor();

        List<Future<String>> futures = new ArrayList<>();

        // Real-world use case: scraping or calling multiple external APIs
        for (int i = 0; i < 5; i++) {
            int id = i;
            futures.add(exec.submit(() -> {
                Thread.sleep(300);
                return "Task " + id + " executed by: " + Thread.currentThread();
            }));
        }

        for (Future<String> f : futures)
            System.out.println(f.get());

        exec.close();
    }

    // ------------------------------------------------------------
    // A2. Pinning Example (synchronized causes pin)
    // ------------------------------------------------------------
    static final Object PIN_LOCK = new Object();

    static void pinningExample() throws Exception {
        System.out.println("\n[2] Pinning Example — synchronized causes carrier thread pinning\n");

        Thread vt = Thread.startVirtualThread(() -> {
            synchronized (PIN_LOCK) {
                try {
                    // This sleep inside synchronized *PINS* the carrier thread.
                    Thread.sleep(500);
                } catch (Exception e) {
                }
            }
        });

        vt.join();
        System.out.println("Pinning example completed (Run with -Djdk.tracePinnedThreads=full to see warnings)");
    }

    // ------------------------------------------------------------
    // A3. Virtual Thread Per Request — Simulated Mini Web Server
    // ------------------------------------------------------------
    static void virtualThreadPerRequestDemo() throws Exception {
        System.out.println("\n[3] Thread-per-Request Simulation — Virtual Threads shine\n");

        ExecutorService exec = Executors.newVirtualThreadPerTaskExecutor();

        // Simulate 20 incoming HTTP requests
        List<Future<String>> futures = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            int req = i;
            futures.add(exec.submit(() -> {
                // In real server: handle HTTP request
                Thread.sleep(200);
                return "Handled Request " + req + " on " + Thread.currentThread();
            }));
        }

        for (Future<String> f : futures)
            System.out.println(f.get());

        exec.close();
    }

    // ------------------------------------------------------------
    // B1. Structured Concurrency — Failure Handling
    // ------------------------------------------------------------
    static void structuredConcurrencyFailure() throws Exception {
        System.out.println("\n[4] Structured Concurrency — ShutdownOnFailure\n");

        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {

            var user = scope.fork(() -> fetchUser());
            var orders = scope.fork(() -> fetchOrdersFailing());

            scope.join();            // Wait for both
            scope.throwIfFailed();   // Propagate any failure

            System.out.println("User Profile: " + user.get() + ", " + orders.get());
        } catch (Exception e) {
            System.out.println("Structured concurrency caught exception: " + e.getMessage());
        }
    }

    static String fetchUser() throws Exception {
        Thread.sleep(200);
        return "User(John)";
    }

    static String fetchOrdersFailing() throws Exception {
        Thread.sleep(300);
        throw new RuntimeException("Orders service down");
    }

    // ------------------------------------------------------------
    // B2. Structured Concurrency — First Successful Result
    // ------------------------------------------------------------
    static void structuredConcurrencySuccess() throws Exception {
        System.out.println("\n[5] Structured Concurrency — ShutdownOnSuccess\n");

        try (var scope = new StructuredTaskScope.ShutdownOnSuccess<String>()) {

            scope.fork(() -> slowPrimaryDatabase());
            scope.fork(() -> fastCache());
            scope.fork(() -> backupService());

            scope.join();
            String result = scope.result();
            System.out.println("Data loaded from: " + result);
        }
    }

    static String fastCache() throws Exception {
        Thread.sleep(100);
        return "Cache";
    }

    static String slowPrimaryDatabase() throws Exception {
        Thread.sleep(500);
        return "Primary DB";
    }

    static String backupService() throws Exception {
        Thread.sleep(300);
        return "Backup";
    }

    // ------------------------------------------------------------
    // B3. Structured Concurrency with Deadline
    // ------------------------------------------------------------
    static void structuredConcurrencyDeadline() throws Exception {
        System.out.println("\n[6] Structured Concurrency — Deadline Handling\n");

        try (var scope = new StructuredTaskScope<String>()) {

            var result = scope.fork(() -> {
                Thread.sleep(1000);
                return "Slow response";
            });

            Instant deadline = Instant.now().plusMillis(300);

            try {
                scope.joinUntil(deadline); // wait until deadline
                // All tasks finished before deadline
                System.out.println("Task finished: " + result.get());
            } catch (TimeoutException e) {
                // Some tasks did not finish
                System.out.println("Deadline reached — task did not finish yet.");
            }
        }
    }

    // ------------------------------------------------------------
    // C4. Replacing Reactor — Simulation Example
    // ------------------------------------------------------------
    static void replacingReactorSimulation() throws Exception {
        System.out.println("\n[7] Replacing Reactor with Virtual Threads — Example\n");

        // BEFORE (Reactor-style async logic)
        CompletableFuture<String> legacy = CompletableFuture.supplyAsync(() -> {
            sleep(200);
            return "Legacy Async Result";
        });

        System.out.println("Legacy CompletableFuture: " + legacy.get());

        // AFTER (Simpler Loom code)
        String loom = Thread.ofVirtual().factory().newThread(() -> {
            sleep(200);
        }).toString();

        ExecutorService exec = Executors.newVirtualThreadPerTaskExecutor();
        String result = exec.submit(() -> {
            sleep(200);
            return "Loom Result";
        }).get();

        System.out.println("Loom Virtual Thread Result: " + result);
        exec.close();
    }

    static void sleep(long ms) {
        try { Thread.sleep(ms); } catch (Exception e) {}
    }

}
