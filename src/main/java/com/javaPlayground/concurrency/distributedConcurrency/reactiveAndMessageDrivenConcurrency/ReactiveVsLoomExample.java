package com.javaPlayground.concurrency.distributedConcurrency.reactiveAndMessageDrivenConcurrency;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.concurrent.Executors;

public class ReactiveVsLoomExample {

    public static void main(String[] args) throws Exception {

//        System.out.println("\n=== 1️⃣ Cold Stream (Reactor) ===");
//        coldStreamExample();
//
        System.out.println("\n=== 2️⃣ Hot Stream (Reactor) ===");
        hotStreamExample();
//
//        System.out.println("\n=== 3️⃣ Backpressure (Reactor) ===");
//        backpressureExample();
//
//        System.out.println("\n=== 4️⃣ Scheduler Selection ===");
//        schedulerExample();
//
//        System.out.println("\n=== 5️⃣ Loom Virtual Threads ===");
//        loomExample();
    }

    /* ---------------------------------------------------------
       1️⃣ Cold Stream — per subscriber execution
       --------------------------------------------------------- */
    static void coldStreamExample() {
        Flux<Integer> cold = Flux.range(1, 5)
                .doOnNext(i -> log("Producing " + i));

        cold.subscribe(i -> log("Subscriber A received " + i));
        cold.subscribe(i -> log("Subscriber B received " + i));
    }

    /* ---------------------------------------------------------
       2️⃣ Hot Stream — shared live source
       --------------------------------------------------------- */
    static void hotStreamExample() {
        Flux<Long> hot = Flux.interval(Duration.ofMillis(300))
                .publish()
                .autoConnect();

        hot.subscribe(i -> log("Subscriber A " + i));

        sleep(700);

        hot.subscribe(i -> log("Subscriber B " + i));

        sleep(1200);
    }

    /* ---------------------------------------------------------
       3️⃣ Backpressure — slow consumer
       --------------------------------------------------------- */
    static void backpressureExample() {
        Flux<Long> fastProducer =
                Flux.interval(Duration.ofMillis(10)) // very fast
                        .onBackpressureDrop(i -> log("Dropped: " + i));

        fastProducer
                .publishOn(Schedulers.parallel())
                .subscribe(i -> {
                    log("Processing: " + i);
                    sleep(200); // slow consumer
                });

        sleep(3000);
    }

    /* ---------------------------------------------------------
       4️⃣ Scheduler Selection
       --------------------------------------------------------- */
    static void schedulerExample() {
        Flux.range(1, 100)
                .publishOn(Schedulers.boundedElastic()) // blocking-safe
                .map(i -> {
                    log("Blocking call: " + i);
                    sleep(30);
                    return i;
                })
                .subscribe(i -> log("Done: " + i));
    }

    /* ---------------------------------------------------------
       5️⃣ Loom — Thread-per-task
       --------------------------------------------------------- */
    static void loomExample() throws Exception {
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 1; i <= 5; i++) {
                int task = i;
                executor.submit(() -> {
                    log("Virtual thread handling task " + task);
                    sleep(300);
                });
            }
        }
    }

    /* ---------------------------------------------------------
       Helpers
       --------------------------------------------------------- */
    static void log(String msg) {
        System.out.printf("[%s] %s%n",
                Thread.currentThread().getName(), msg);
    }

    static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ignored) {}
    }
}

