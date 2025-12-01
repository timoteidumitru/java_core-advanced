package com.javaPlayground.concurrency.parallelStreams;

import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class RangeSpliterator implements Spliterator<Integer> {

    private int current;
    private final int end;

    public RangeSpliterator(int start, int end) {
        this.current = start;
        this.end = end;
    }

    @Override
    public boolean tryAdvance(Consumer<? super Integer> action) {
        if (current < end) {
            action.accept(current++);
            return true;
        }
        return false;
    }

    @Override
    public Spliterator<Integer> trySplit() {
        int remaining = end - current;
        if (remaining <= 1) {
            return null; // too small to split
        }

        int mid = current + remaining / 2;
        Spliterator<Integer> spliterator = new RangeSpliterator(current, mid);
        this.current = mid;
        return spliterator;
    }

    @Override
    public long estimateSize() {
        return end - current;
    }

    @Override
    public int characteristics() {
        return Spliterator.ORDERED
                | Spliterator.SIZED
                | Spliterator.SUBSIZED
                | Spliterator.NONNULL
                | Spliterator.IMMUTABLE;
    }

    public static void main(String[] args) {

        Spliterator<Integer> sp = new RangeSpliterator(1, 20);
        Stream<Integer> stream = StreamSupport.stream(sp, true);

        int sum = stream
                .peek(n -> System.out.println(
                        "Thread " + Thread.currentThread().getName() + " = " + n))
                .mapToInt(i -> i)
                .sum();

        System.out.println("Sum = " + sum);
    }
}

