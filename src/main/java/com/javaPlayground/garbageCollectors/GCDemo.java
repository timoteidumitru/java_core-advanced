package com.javaPlayground.garbageCollectors;

import java.lang.management.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class GCDemo {
    public static void main(String[] args) throws InterruptedException {
        // Display initial memory status
        printMemoryUsage("Initial Memory Usage");

        // Create an executor repository with multiple threads
        ExecutorService executor = Executors.newFixedThreadPool(2);

        // Launch multiple threads to simulate memory load
        for (int i = 0; i < 4; i++) {
            executor.submit(new MemoryLoaderTask());
        }

        // Shutdown executor and wait for threads to complete
        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.MINUTES);

        // Display memory status after tasks
        printMemoryUsage("Memory Usage After Tasks");

        // Trigger garbage collection explicitly for demonstration purposes
        System.out.println("Requesting Garbage Collection...");
        System.gc();  // Explicit call to trigger GC (not guaranteed)

        // Wait a little to let GC work
        TimeUnit.SECONDS.sleep(5);

        // Final memory usage after GC
        printMemoryUsage("Final Memory Usage After GC");
    }

    // Method to print the current memory usage
    public static void printMemoryUsage(String label) {
        MemoryMXBean memoryMxBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage heapMemoryUsage = memoryMxBean.getHeapMemoryUsage();
        final int mb = 1024 * 1024;

        System.out.println("\n--- " + label + " ---");
        System.out.printf("Used: %d MB, Committed: %d MB, Max: %d MB\n",
                heapMemoryUsage.getUsed() / mb,
                heapMemoryUsage.getCommitted() / mb,
                heapMemoryUsage.getMax() / mb);
    }
}


