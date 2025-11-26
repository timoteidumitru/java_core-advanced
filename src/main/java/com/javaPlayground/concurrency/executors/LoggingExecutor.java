package com.javaPlayground.concurrency.executors;

import java.util.concurrent.Executor;

public class LoggingExecutor implements Executor {

    @Override
    public void execute(Runnable command) {
        System.out.println("Executing: " + command);
        new Thread(command).start();  // naive execution
    }

    public static void main(String[] args) {
        Executor executor = new LoggingExecutor();

        executor.execute(() -> {
            System.out.println("Task running in: " + Thread.currentThread().getName());
        });
    }
}
