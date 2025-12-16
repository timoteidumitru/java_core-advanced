package com.javaPlayground.concurrency.CPUvsIoBound;

import java.util.concurrent.*;
import java.util.concurrent.Flow.*;
import java.util.concurrent.atomic.AtomicInteger;

import java.util.concurrent.*;
import java.util.concurrent.Flow.*;
import java.util.concurrent.atomic.AtomicInteger;

import java.util.concurrent.*;
import java.util.concurrent.Flow.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ReactiveExtremeIoExample {

    static final int REQUESTS = 10_000_000;

    public static void main(String[] args) throws Exception {

        CountDownLatch done = new CountDownLatch(1);

        ExecutorService eventLoop = Executors.newFixedThreadPool(2);

        SubmissionPublisher<Integer> publisher =
                new SubmissionPublisher<>(eventLoop, Flow.defaultBufferSize());

        long start = System.nanoTime();

        publisher.subscribe(new Subscriber<>() {

            Subscription subscription;
            AtomicInteger received = new AtomicInteger();

            @Override
            public void onSubscribe(Subscription subscription) {
                this.subscription = subscription;
                subscription.request(1);
            }

            @Override
            public void onNext(Integer item) {
                if (received.incrementAndGet() % 1_000_000 == 0) {
                    System.out.println("Processed: " + received.get());
                }
                subscription.request(1);
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
                done.countDown();
            }

            @Override
            public void onComplete() {
                System.out.println("Reactive processing complete");
                done.countDown();
            }
        });

        for (int i = 0; i < REQUESTS; i++) {
            publisher.submit(i);
        }

        publisher.close();

        // ✅ Wait for stream completion
        done.await();

        // ✅ Shut down executor (THIS was missing)
        eventLoop.shutdown();
        eventLoop.awaitTermination(1, TimeUnit.MINUTES);

        long end = System.nanoTime();

        System.out.println("=================================");
        System.out.println("Reactive Extreme I/O Example");
        System.out.println("Requests : " + REQUESTS);
        System.out.println("Time     : " + (end - start) / 1_000_000 + " ms");
        System.out.println("Threads  : 2 (event-loop style)");
        System.out.println("=================================");
    }

}



