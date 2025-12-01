package com.javaPlayground.concurrency.parallelStreams;

import java.util.List;
import java.util.stream.IntStream;

public class StreamPerfTest {

    public static void main(String[] args) {
        List<Integer> nums = IntStream.range(1, 2_000_000)
                .boxed()
                .toList();

        long t1 = System.currentTimeMillis();
        long sum1 = nums.stream()
                .mapToLong(StreamPerfTest::heavy)
                .sum();
        long t2 = System.currentTimeMillis();

        long t3 = System.currentTimeMillis();
        long sum2 = nums.parallelStream()
                .mapToLong(StreamPerfTest::heavy)
                .sum();
        long t4 = System.currentTimeMillis();

        System.out.println("Sequential: " + (t2 - t1) + " ms");
        System.out.println("Parallel:   " + (t4 - t3) + " ms");
    }

    private static long heavy(long x) {
        long r = x;
        for (int i = 0; i < 1_000; i++) {
            r = r * 1664525 + 1013904223 ^ 0xDEADBEEFL;
        }
        return r;
    }
}

