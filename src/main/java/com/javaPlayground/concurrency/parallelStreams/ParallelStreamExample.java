package com.javaPlayground.concurrency.parallelStreams;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ParallelStreamExample {

    private static final int SIZE = 10_000_000;

    public static void main(String[] args) throws Exception {

        List<Integer> numbers = IntStream.rangeClosed(1, SIZE)
                .boxed()
                .toList();

        System.out.println("Sequential sum:");
        runBenchmark(numbers.stream());

        System.out.println("\nParallel sum (common pool):");
        runBenchmark(numbers.parallelStream());

        System.out.println("\nParallel sum (custom pool):");
        ForkJoinPool customPool = new ForkJoinPool(16);
        customPool.submit(() -> runBenchmark(numbers.parallelStream())).get();
    }

    private static void runBenchmark(Stream<Integer> stream) {
        long start = System.currentTimeMillis();

        long result = stream
                .mapToLong(ParallelStreamExample::heavyComputation)
                .sum();

        long end = System.currentTimeMillis();
        System.out.println("Sum = " + result
                + " | time = " + (end - start) + " ms");
    }

    private static long heavyComputation(int x) {
        // simulate expensive computation
        for (int i = 0; i < 200; i++) {
            Math.sqrt(x + i);
        }
        return x;
    }

}
