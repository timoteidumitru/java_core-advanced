package com.javaPlayground.concurrency.CPUvsIOBound;

import java.util.concurrent.*;

public class CpuBoundForkJoinExample {

    static class SumTask extends RecursiveTask<Long> {
        final long start, end;

        SumTask(long start, long end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected Long compute() {
            if (end - start <= 10_000) {
                long sum = 0;
                for (long i = start; i < end; i++) sum += i;
                return sum;
            }
            long mid = (start + end) / 2;
            SumTask left = new SumTask(start, mid);
            SumTask right = new SumTask(mid, end);
            left.fork();
            return right.compute() + left.join();
        }
    }

    public static void main(String[] args) {
        long start = System.nanoTime();

        ForkJoinPool pool = ForkJoinPool.commonPool();
        long result = pool.invoke(new SumTask(0, 100_000_000));

        long end = System.nanoTime();

        System.out.println("ForkJoin CPU-bound");
        System.out.println("Result: " + result);
        System.out.println("Time  : " + (end - start) / 1_000_000 + " ms");
    }
}

