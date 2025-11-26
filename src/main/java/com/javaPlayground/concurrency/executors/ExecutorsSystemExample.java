package com.javaPlayground.concurrency.executors;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ExecutorsSystemExample {

    // ==============================
    //  SHARED SERVICES
    // ==============================

    private static final ExecutorService orderProcessingPool =
            Executors.newFixedThreadPool(4, r -> new Thread(r, "order-worker"));

    private static final ExecutorService apiCallPool =
            Executors.newCachedThreadPool(r -> new Thread(r, "api-worker"));

    private static final ScheduledExecutorService scheduler =
            Executors.newScheduledThreadPool(2);

    private static final ExecutorService loggingPipeline =
            Executors.newSingleThreadExecutor(r -> new Thread(r, "logger"));

    private static final AtomicInteger ORDER_ID = new AtomicInteger(1);


    // ==============================
    //  DOMAIN CLASSES
    // ==============================

    static class Order {
        final int id;
        final String item;

        Order(String item) {
            this.id = ORDER_ID.getAndIncrement();
            this.item = item;
        }

        @Override
        public String toString() {
            return "Order#" + id + " (" + item + ")";
        }
    }

    static class NotificationService {
        void sendNotification(Order order) {
            apiCallPool.submit(() -> {
                simulateDelay(500, 1200);
                log("Notification sent for " + order);
            });
        }
    }

    static class OrderProcessor {
        void process(Order order) {
            orderProcessingPool.submit(() -> {
                log("Processing " + order);

                // simulate some CPU + I/O work
                simulateDelay(700, 1500);

                log("Completed " + order);

                // send notification after processing
                NOTIFICATION_SERVICE.sendNotification(order);
            });
        }
    }

    static class CleanupService {
        void cleanupOldRecords() {
            log("Cleanup job running…");
            simulateDelay(300, 600);
            log("Cleanup job finished.");
        }
    }


    // ==============================
    //  SYSTEM SERVICES
    // ==============================

    private static final OrderProcessor ORDER_PROCESSOR = new OrderProcessor();
    private static final NotificationService NOTIFICATION_SERVICE = new NotificationService();
    private static final CleanupService CLEANUP_SERVICE = new CleanupService();


    // ==============================
    //  LOGGING PIPELINE
    // ==============================

    private static void log(String message) {
        loggingPipeline.submit(() -> {
            System.out.println(Thread.currentThread().getName()
                    + " | " + message);
        });
    }


    // ==============================
    //  UTILS
    // ==============================

    private static void simulateDelay(int min, int max) {
        try {
            Thread.sleep(min + new Random().nextInt(max - min));
        } catch (InterruptedException ignored) {}
    }


    // ==============================
    //  MAIN SYSTEM STARTUP
    // ==============================

    public static void main(String[] args) throws InterruptedException {

        log("Starting Order System...");

        // 1) HEARTBEAT (every 2 seconds)
        scheduler.scheduleAtFixedRate(() -> {
            log("Heartbeat — system alive at " + System.currentTimeMillis());
        }, 1, 2, TimeUnit.SECONDS);

        // 2) CLEANUP JOB (every 5 seconds)
        scheduler.scheduleWithFixedDelay(() -> {
            CLEANUP_SERVICE.cleanupOldRecords();
        }, 3, 5, TimeUnit.SECONDS);

        // 3) SIMULATE ORDERS COMING IN
        scheduler.scheduleAtFixedRate(() -> {
            Order order = new Order("Laptop");
            log("Received " + order);
            ORDER_PROCESSOR.process(order);
        }, 1, 1, TimeUnit.SECONDS);

        // Let system run for 20 seconds for demonstration
        Thread.sleep(20000);

        shutdownSystem();
    }


    // ==============================
    //  SHUTDOWN HANDLING
    // ==============================

    private static void shutdownSystem() throws InterruptedException {
        log("Shutting down system...");

        scheduler.shutdown();
        orderProcessingPool.shutdown();
        apiCallPool.shutdown();
        loggingPipeline.shutdown();

        scheduler.awaitTermination(3, TimeUnit.SECONDS);
        orderProcessingPool.awaitTermination(3, TimeUnit.SECONDS);
        apiCallPool.awaitTermination(3, TimeUnit.SECONDS);
        loggingPipeline.awaitTermination(3, TimeUnit.SECONDS);

        System.out.println("System shutdown complete.");
    }
}
