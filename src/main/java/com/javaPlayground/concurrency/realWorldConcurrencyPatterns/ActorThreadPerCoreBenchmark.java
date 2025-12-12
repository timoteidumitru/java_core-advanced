package com.javaPlayground.concurrency.realWorldConcurrencyPatterns;

import java.util.concurrent.*;
import java.util.function.Consumer;

public class ActorThreadPerCoreBenchmark {

    // -----------------------------
    // Generic Actor class
    // -----------------------------
    static class Actor<T> {
        private final BlockingQueue<T> mailbox = new LinkedBlockingQueue<>();
        private final Thread worker;

        public Actor(String name, Consumer<T> handler) {
            worker = new Thread(() -> {
                try {
                    while (!Thread.currentThread().isInterrupted()) {
                        T message = mailbox.take();
                        handler.accept(message);
                    }
                } catch (InterruptedException ignored) {
                }
            }, name);
            worker.start();
        }

        public void send(T message) {
            mailbox.offer(message);
        }

        public void stop() {
            worker.interrupt();
        }
    }

    // -----------------------------
    // Benchmark main
    // -----------------------------
    public static void main(String[] args) throws InterruptedException {
        final int cores = Runtime.getRuntime().availableProcessors();
        final int messagesPerActor = 1_000_000; //
        final int totalMessages = cores * messagesPerActor;

        System.out.println("CPU cores detected: " + cores);
        System.out.println("Total messages to process: " + totalMessages);

        CountDownLatch latch = new CountDownLatch(totalMessages);

        // Actor chain: A -> B -> C
        Actor<Integer> actorC = new Actor<>("Actor-C", msg -> latch.countDown());

        Actor<Integer> actorB = new Actor<>("Actor-B", actorC::send);

        Actor<Integer>[] actorsA = new Actor[cores];
        for (int i = 0; i < cores; i++) {
            actorsA[i] = new Actor<>("Actor-A-" + i, actorB::send);
        }

        // -----------------------------
        // Start benchmark
        // -----------------------------
        long start = System.nanoTime();

        // Send messages in parallel, one batch per actor
        ExecutorService senderPool = Executors.newFixedThreadPool(cores);
        for (int i = 0; i < cores; i++) {
            final int idx = i;
            senderPool.submit(() -> {
                for (int j = 0; j < messagesPerActor; j++) {
                    actorsA[idx].send(j);
                }
            });
        }

        latch.await();
        long end = System.nanoTime();

        // Shutdown
        senderPool.shutdownNow();
        for (Actor<Integer> actor : actorsA) actor.stop();
        actorB.stop();
        actorC.stop();

        // Calculate throughput
        double seconds = (end - start) / 1_000_000_000.0;
        double throughput = totalMessages / seconds;

        System.out.println("=========================================");
        System.out.println("Actor Model / Thread-per-Core Benchmark");
        System.out.println("Total messages: " + totalMessages);
        System.out.printf("Time elapsed: %.3f sec%n", seconds);
        System.out.printf("Throughput: %.2f msgs/sec%n", throughput);
        System.out.println("=========================================");
    }
}

