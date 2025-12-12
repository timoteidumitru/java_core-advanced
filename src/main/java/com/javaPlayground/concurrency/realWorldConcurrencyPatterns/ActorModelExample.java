package com.javaPlayground.concurrency.realWorldConcurrencyPatterns;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

public class ActorModelExample {

    static class Actor<T> {
        private final BlockingQueue<T> mailbox = new LinkedBlockingQueue<>();
        private final Thread worker;

        public Actor(String name, Consumer<T> handler) {
            worker = new Thread(() -> {
                try {
                    while (true) {
                        T message = mailbox.take();  // process one message at a time
                        handler.accept(message);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
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

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(5); // number of test messages

        // Actor C just prints and counts down
        Actor<String> actorC = new Actor<>("Actor-C", msg -> {
            System.out.println("Actor C received: " + msg);
            latch.countDown();
        });

        // Actor B forwards messages to Actor C
        Actor<String> actorB = new Actor<>("Actor-B", msg -> {
            System.out.println("Actor B forwarding: " + msg);
            actorC.send(msg);
        });

        // Actor A sends messages to Actor B
        Actor<String> actorA = new Actor<>("Actor-A", msg -> {
            System.out.println("Actor A sending: " + msg);
            actorB.send(msg);
        });

        // Send some test messages
        for (int i = 1; i <= 5; i++) {
            actorA.send("Message-" + i);
        }

        // Wait for all messages to be processed
        latch.await();

        // Stop all actors
        actorA.stop();
        actorB.stop();
        actorC.stop();

        System.out.println("All messages processed.");
    }

}
