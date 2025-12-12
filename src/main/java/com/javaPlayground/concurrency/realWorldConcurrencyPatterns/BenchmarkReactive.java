package com.javaPlayground.concurrency.realWorldConcurrencyPatterns;

import java.util.concurrent.*;
import java.util.concurrent.Flow.*;

public class BenchmarkReactive {

    static final int TASKS = 100_000_000;

    public static void main(String[] args) throws Exception {
        SubmissionPublisher<Integer> publisher = new SubmissionPublisher<>();

        CountDownLatch latch = new CountDownLatch(TASKS);

        publisher.subscribe(new Subscriber<Integer>() {
            Subscription subscription;

            @Override public void onSubscribe(Subscription s) {
                subscription = s;
                subscription.request(TASKS);
            }

            @Override public void onNext(Integer item) {
                latch.countDown();
            }
            @Override public void onError(Throwable t) {}
            @Override public void onComplete() {}
        });

        long start = System.nanoTime();

        for (int i = 0; i < TASKS; i++) {
            publisher.submit(i);
        }

        latch.await();

        long elapsed = System.nanoTime() - start;

        System.out.println("Reactive Streams Throughput: " +
                (TASKS * 1_000_000_000L / elapsed) + " ops/sec");

        publisher.close();
    }
}

