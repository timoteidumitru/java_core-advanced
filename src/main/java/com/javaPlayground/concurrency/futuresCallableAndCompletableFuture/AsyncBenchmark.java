package com.javaPlayground.concurrency.futuresCallableAndCompletableFuture;

import java.util.*;
import java.util.concurrent.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class AsyncBenchmark {

    private static final int TASK_COUNT = 1000;
    private static final int IO_LATENCY_MS = 10;

    // Simulated remote call
    static String fakeApiCall(int id) {
        try { Thread.sleep(IO_LATENCY_MS); } catch (InterruptedException ignored) {}
        return "Result " + id;
    }

    public static void main(String[] args) throws Exception {

        System.out.println("===== BEGINNING BENCHMARK =====");
        Thread.sleep(1000);

        // 1️⃣ ExecutorService benchmark
        long t1 = executorServiceBenchmark();
        System.out.println("ExecutorService: " + t1 + " ms");

        // 2️⃣ CompletableFuture benchmark
        long t2 = completableFutureBenchmark();
        System.out.println("CompletableFuture: " + t2 + " ms");

        // 3️⃣ Reactor benchmark
        long t3 = reactorBenchmark();
        System.out.println("Reactor Flux: " + t3 + " ms");
    }

    // ---------------------------------------------------
    // Benchmark 1: ExecutorService + Future
    // ---------------------------------------------------
    static long executorServiceBenchmark() throws Exception {
        ExecutorService pool = Executors.newFixedThreadPool(50);
        long start = System.currentTimeMillis();

        List<Future<String>> futures = new ArrayList<>();
        for (int i = 0; i < TASK_COUNT; i++) {
            int id = i;
            futures.add(pool.submit(() -> fakeApiCall(id)));
        }

        for (Future<String> f : futures) f.get();

        pool.shutdown();
        return System.currentTimeMillis() - start;
    }

    // ---------------------------------------------------
    // Benchmark 2: CompletableFuture
    // ---------------------------------------------------
    static long completableFutureBenchmark() {
        ExecutorService pool = Executors.newFixedThreadPool(50);
        long start = System.currentTimeMillis();

        List<CompletableFuture<String>> futures = new ArrayList<>();
        for (int i = 0; i < TASK_COUNT; i++) {
            int id = i;
            futures.add(CompletableFuture.supplyAsync(() -> fakeApiCall(id), pool));
        }

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        pool.shutdown();
        return System.currentTimeMillis() - start;
    }

    // ---------------------------------------------------
    // Benchmark 3: Reactor (Flux)
    // ---------------------------------------------------
    static long reactorBenchmark() {
        long start = System.currentTimeMillis();

        Flux.range(0, TASK_COUNT)
                .flatMap(i -> Mono.fromCallable(() -> fakeApiCall(i)).subscribeOn(reactor.core.scheduler.Schedulers.parallel()))
                .blockLast();

        return System.currentTimeMillis() - start;
    }
}

