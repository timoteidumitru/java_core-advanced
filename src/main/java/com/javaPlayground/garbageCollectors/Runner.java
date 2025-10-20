package com.javaPlayground.garbageCollectors;

import java.lang.management.*;
import com.sun.management.HotSpotDiagnosticMXBean;
import java.util.Arrays;
import java.util.List;

public class Runner {

    public static void main(String[] args) {
        Runtime runtime = Runtime.getRuntime();

        // Processor
        int proc = runtime.availableProcessors();
        System.out.println("JVM Processor count: " + proc);

        // Memory
        final int mb = 1024 * 1024;
        System.out.println("JVM Memory Heap Size: " + runtime.maxMemory() / mb + " MB");

        // Garbage Collector (ergonomics only)
        Arrays.asList("UseSerialGC", "UseG1GC", "UseParallelGC").forEach(Runner::printVmOption);

        // Get current heap and GC details
        printHeapMemoryUsage();
        printGcDetails();
        printMemoryPoolDetails();
        printGcOverhead();
    }

    // Fetch VM options
    public static void printVmOption(String name) {
        System.out.printf("%s: %s\n", name, getHotSpotVmOptionValue(name));
    }

    private static String getHotSpotVmOptionValue(String name) {
        try {
            HotSpotDiagnosticMXBean hotSpotMxBean = ManagementFactory.getPlatformMXBean(HotSpotDiagnosticMXBean.class);
            return hotSpotMxBean.getVMOption(name).getValue();
        } catch (Exception e) {
            return "Option not found or not supported.";
        }
    }

    // Method to print current heap memory usage
    public static void printHeapMemoryUsage() {
        MemoryMXBean memoryMxBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage heapMemoryUsage = memoryMxBean.getHeapMemoryUsage();

        System.out.println("\n--- Current Heap Memory Usage ---");
        System.out.printf("Init: %d MB, Used: %d MB, Committed: %d MB, Max: %d MB\n",
                heapMemoryUsage.getInit() / (1024 * 1024),
                heapMemoryUsage.getUsed() / (1024 * 1024),
                heapMemoryUsage.getCommitted() / (1024 * 1024),
                heapMemoryUsage.getMax() / (1024 * 1024));
    }

    // Method to print garbage collector details
    public static void printGcDetails() {
        List<GarbageCollectorMXBean> gcMxBeans = ManagementFactory.getGarbageCollectorMXBeans();

        System.out.println("\n--- Garbage Collector Details ---");
        for (GarbageCollectorMXBean gcMxBean : gcMxBeans) {
            System.out.printf("Name: %s, Collection Count: %d, Collection Time: %d ms\n",
                    gcMxBean.getName(),
                    gcMxBean.getCollectionCount(),
                    gcMxBean.getCollectionTime());
        }
    }

    // Method to print memory pool details (including Eden, Survivor, OldGen, etc.)
    public static void printMemoryPoolDetails() {
        List<MemoryPoolMXBean> memoryPoolMxBeans = ManagementFactory.getMemoryPoolMXBeans();

        System.out.println("\n--- Memory Pool Details ---");
        for (MemoryPoolMXBean memoryPoolMxBean : memoryPoolMxBeans) {
            MemoryUsage usage = memoryPoolMxBean.getUsage();
            System.out.printf("Name: %s, Type: %s, Used: %d MB, Committed: %d MB, Max: %d MB\n",
                    memoryPoolMxBean.getName(),
                    memoryPoolMxBean.getType(),
                    usage.getUsed() / (1024 * 1024),
                    usage.getCommitted() / (1024 * 1024),
                    usage.getMax() / (1024 * 1024));
        }
    }

    // Method to print GC overhead (pause time and throughput)
    public static void printGcOverhead() {
        List<GarbageCollectorMXBean> gcMxBeans = ManagementFactory.getGarbageCollectorMXBeans();
        long totalGcTime = 0;
        long totalGcCollections = 0;

        for (GarbageCollectorMXBean gcMxBean : gcMxBeans) {
            totalGcTime += gcMxBean.getCollectionTime();
            totalGcCollections += gcMxBean.getCollectionCount();
        }

        System.out.println("\n--- GC Overhead ---");
        System.out.printf("Total GC Time: %d ms, Total GC Collections: %d\n", totalGcTime, totalGcCollections);

        // Optionally, you can calculate GC throughput if the application's runtime is available.
        long uptime = ManagementFactory.getRuntimeMXBean().getUptime(); // in milliseconds
        double gcThroughput = (1 - ((double) totalGcTime / uptime)) * 100;

        System.out.printf("JVM Uptime: %d ms\n", uptime);
        System.out.printf("GC Throughput: %.2f%% (Percentage of time not spent in GC)\n", gcThroughput);
    }
}
