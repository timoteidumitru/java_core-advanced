package com.javaPlayground.concurrency.modernConcurrency;

import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

public class ReactiveStreamExample {

    public static void main(String[] args) throws Exception {

        // Publisher
        try (SubmissionPublisher<Integer> publisher = new SubmissionPublisher<>()) {

            // Subscriber
            Flow.Subscriber<Integer> subscriber = new Flow.Subscriber<>() {
                private Flow.Subscription subscription;

                @Override
                public void onSubscribe(Flow.Subscription subscription) {
                    this.subscription = subscription;
                    System.out.println("Subscribed");
                    subscription.request(1); // request first item
                }

                @Override
                public void onNext(Integer item) {
                    if (item % 10_000_000 == 0) {
                        System.out.println("Received: " + item);
                    }
                    subscription.request(1); // request next item
                }

                @Override
                public void onError(Throwable throwable) {
                    throwable.printStackTrace();
                }

                @Override
                public void onComplete() {
                    System.out.println("Done");
                }
            };

            publisher.subscribe(subscriber);

            // Publish events
            for (int i = 1; i <= 100_000_000; i++) {
                if (i % 10_000_000 == 0) {
                    System.out.printf("Publishing " + i + "%n");
                }

                publisher.submit(i);
            }
        }

        Thread.sleep(500); // allow async events to complete
    }
}
