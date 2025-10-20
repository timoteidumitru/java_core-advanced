package com.javaPlayground.garbageCollectors;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.util.ArrayList;
import java.util.List;

// A task to simulate memory pressure by creating a lot of objects
class MemoryLoaderTask implements Runnable {
    @Override
    public void run() {
        List<byte[]> memoryHog = new ArrayList<>();
        final int chunkSize = 10 * 1024 * 1024 * 100; // 100MB chunks

        try {
            // Simulate memory load by allocating large chunks
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName().split("pool-1-")[1] + " - Allocating 100MB of memory...");
                memoryHog.add(new byte[chunkSize]);  // Allocate 100 MB
                Thread.sleep(1000);  // Simulate work
                printMemoryUsage(Thread.currentThread().getName().split("pool-1-")[1] + " - After Allocation " + (i + 1));
            }
        } catch (OutOfMemoryError | InterruptedException e) {
            System.out.println("Requesting Garbage Collection...");
            System.gc();  // Explicit call to trigger GC (not guaranteed)
            System.out.println("Out of memory encountered! GC should kick in.");
        }

        // Clear the list, making the objects eligible for garbage collection
        memoryHog.clear();
        System.out.println(Thread.currentThread().getName().split("pool-1-")[1] + " - Finished and cleared memory.");
    }

    // Print memory usage for individual threads
    public void printMemoryUsage(String label) {
        MemoryMXBean memoryMxBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage heapMemoryUsage = memoryMxBean.getHeapMemoryUsage();
        final int mb = 1024 * 1024;

        System.out.println("\n--- " + label + " ---");
        System.out.printf("Used: %d MB, Committed: %d MB\n",
                heapMemoryUsage.getUsed() / mb,
                heapMemoryUsage.getCommitted() / mb);
    }
}