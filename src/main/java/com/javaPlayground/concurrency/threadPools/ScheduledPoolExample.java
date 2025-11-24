package com.javaPlayground.concurrency.threadPools;

import java.util.concurrent.*;

public class ScheduledPoolExample {

    public static void main(String[] args) {

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);

        Runnable heartbeat = () ->
                System.out.println("Heartbeat from " + Thread.currentThread().getName());

        scheduler.scheduleAtFixedRate(heartbeat, 1, 2, TimeUnit.SECONDS);
    }
}

