package com.javaPlayground.concurrency.modernConcurrency;

import java.util.concurrent.StructuredTaskScope;

public class StructuredConcurrencyExample {

    public static String fetchUser() throws Exception {
        Thread.sleep(200);
        return "John Doe";
    }

    public static String fetchOrders() throws Exception {
        Thread.sleep(300);
        return "5 orders";
    }

    public static void main(String[] args) throws Exception {

        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {

            StructuredTaskScope.Subtask<String> userTask = scope.fork(StructuredConcurrencyExample::fetchUser);
            StructuredTaskScope.Subtask<String> orderTask = scope.fork(StructuredConcurrencyExample::fetchOrders);

            scope.join();           // wait for both
            scope.throwIfFailed();  // propagate errors

            String user = userTask.get();
            String orders = orderTask.get();

            System.out.println("Result: " + user + " has " + orders);
        }
    }

}
