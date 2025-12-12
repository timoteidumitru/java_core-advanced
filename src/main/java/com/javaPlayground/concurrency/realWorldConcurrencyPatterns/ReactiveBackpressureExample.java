package com.javaPlayground.concurrency.realWorldConcurrencyPatterns;

import java.util.concurrent.*;
import java.util.concurrent.Flow.*;

public class ReactiveBackpressureExample {

    public static void main(String[] args) throws InterruptedException {
        SubmissionPublisher<String> publisher = new SubmissionPublisher<>();

        SlowSubscriber subscriber = new SlowSubscriber();

        publisher.subscribe(subscriber);

        // Fast producer: produces 20 elements rapidly
        for (int i = 0; i < 20; i++) {
            System.out.println("[Producer] Publishing Task " + i);
            publisher.submit("Task " + i);
            Thread.sleep(50); // fast
        }

        Thread.sleep(3000);
        publisher.close();
    }

    // Slow consumer implementing backpressure
    static class SlowSubscriber implements Subscriber<String> {

        private Subscription subscription;

        @Override
        public void onSubscribe(Subscription subscription) {
            this.subscription = subscription;
            System.out.println("[Subscriber] Subscribed");
            subscription.request(1); // request FIRST item
        }

        @Override
        public void onNext(String item) {
            System.out.println("   [Subscriber] Processing " + item);
            try {
                Thread.sleep(300); // slow consumer
            } catch (InterruptedException ignored) {}

            subscription.request(1); // request next item
        }

        @Override
        public void onError(Throwable t) {
            t.printStackTrace();
        }

        @Override
        public void onComplete() {
            System.out.println("[Subscriber] Completed");
        }
    }
}

