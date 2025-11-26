package com.javaPlayground.concurrency.executors;

import java.util.concurrent.*;

public class ScheduledExecutor {

    public static void main(String[] args) {

        ScheduledExecutorService scheduler =
                Executors.newScheduledThreadPool(2);

        // Run after 2 seconds delay
        scheduler.schedule(() -> {
            System.out.println("Delayed task executed.");
        }, 2, TimeUnit.SECONDS);

        // Run every 1s, respecting execution time (fixed rate)
        scheduler.scheduleAtFixedRate(() -> {
            System.out.println("Heartbeat at " + System.currentTimeMillis());
        }, 1, 1, TimeUnit.SECONDS);

        // Run every 3s, but waits for task to finish (fixed delay)
        scheduler.scheduleWithFixedDelay(() -> {
            System.out.println("Monitoring...");
            try { Thread.sleep(1200); } catch (InterruptedException ignored) {}
        }, 1, 3, TimeUnit.SECONDS);
    }
}

