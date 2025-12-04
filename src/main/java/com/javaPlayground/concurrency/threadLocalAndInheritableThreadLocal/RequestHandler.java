package com.javaPlayground.concurrency.threadLocalAndInheritableThreadLocal;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RequestHandler {

    private static final ExecutorService executor = Executors.newFixedThreadPool(4);

    public void handleRequest(String payload) {
        executor.submit(() -> {
            try {
                String id = UUID.randomUUID().toString();
                RequestContext.set(id);

                log("Start processing: " + payload);
                Thread.sleep(100);
                log("Finished: " + payload);

            } catch (Exception ignored) {
            } finally {
                RequestContext.clear();
            }
        });
    }

    private void log(String msg) {
        System.out.printf("[%s] %s%n", RequestContext.get(), msg);
    }

    public static void main(String[] args) {
        RequestHandler handler = new RequestHandler();

        handler.handleRequest("A");
        handler.handleRequest("B");
        handler.handleRequest("C");

        try { Thread.sleep(1000); } catch (Exception ignored) {}
    }
}

