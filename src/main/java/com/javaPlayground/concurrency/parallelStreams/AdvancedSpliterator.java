package com.javaPlayground.concurrency.parallelStreams;

import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

public class AdvancedSpliterator<T> implements Spliterator<T> {

    private final T[] array;
    private int start;
    private final int end;
    private static final int THRESHOLD = 1_000;

    public AdvancedSpliterator(T[] array) {
        this(array, 0, array.length);
    }

    public AdvancedSpliterator(T[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    public boolean tryAdvance(Consumer<? super T> action) {
        if (start < end) {
            action.accept(array[start++]);
            return true;
        }
        return false;
    }

    @Override
    public Spliterator<T> trySplit() {
        int size = end - start;

        if (size <= THRESHOLD) {
            return null;
        }

        int mid = start + size / 2;
        Spliterator<T> split = new AdvancedSpliterator<>(array, start, mid);
        start = mid;
        return split;
    }

    @Override
    public long estimateSize() {
        return end - start;
    }

    @Override
    public int characteristics() {
        return Spliterator.ORDERED
                | Spliterator.IMMUTABLE
                | Spliterator.SIZED
                | Spliterator.SUBSIZED
                | Spliterator.NONNULL;
    }


    public static void main(String[] args) {

        Integer[] data = IntStream.rangeClosed(1, 100_000_000)
                .boxed()
                .toArray(Integer[]::new);

        Spliterator<Integer> sp = new AdvancedSpliterator<>(data);

        long start = System.currentTimeMillis();

        long result = StreamSupport.stream(sp, true)
                .mapToLong(AdvancedSpliterator::heavyWork)
                .sum();

        long end = System.currentTimeMillis();

        System.out.println("Result = " + result
                + ", time = " + (end - start) + " ms");
    }

    private static long heavyWork(long x) {
        for (int i = 0; i < 200; i++) {
            Math.sqrt(x + i);
        }
        return x;
    }
}

