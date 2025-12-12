package com.javaPlayground.concurrency.realWorldConcurrencyPatterns;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.nio.ByteBuffer;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class DisruptorBenchmark {

    // Total events to publish
    static final int ITERATIONS = 1_000_000_000;

    // Event type stored inside the ring buffer
    public static class LongEvent {
        private long value;

        public void set(long value) {
            this.value = value;
        }
        public long get() {
            return value;
        }
    }

    // Factory to create events inside the ring buffer
    public static class LongEventFactory implements EventFactory<LongEvent> {
        @Override
        public LongEvent newInstance() {
            return new LongEvent();
        }
    }

    // Event handler that consumes events on the other side
    public static class LongEventHandler implements EventHandler<LongEvent> {
        long sum = 0;

        @Override
        public void onEvent(LongEvent event, long sequence, boolean endOfBatch) {
            sum += event.get(); // simple workload
        }
    }

    public static void main(String[] args) throws Exception {

        int ringBufferSize = 1024 * 8; // power of 2, required
        LongEventFactory factory = new LongEventFactory();
        ThreadFactory threadFactory = Executors.defaultThreadFactory();

        // Construct Disruptor instance
        Disruptor<LongEvent> disruptor = new Disruptor<>(
                factory,
                ringBufferSize,
                threadFactory,
                ProducerType.SINGLE,       // (Change to MULTI for multi-producer)
                new BusySpinWaitStrategy() // Fastest wait strategy
        );

        LongEventHandler handler = new LongEventHandler();

        // Connect handler
        disruptor.handleEventsWith(handler);

        // Start the Disruptor
        RingBuffer<LongEvent> ringBuffer = disruptor.start();

        ByteBuffer bb = ByteBuffer.allocate(10);

        long start = System.nanoTime();

        for (int i = 0; i < ITERATIONS; i++) {
            long seq = ringBuffer.next();          // Claim next slot
            try {
                LongEvent event = ringBuffer.get(seq);
                event.set(i);
            } finally {
                ringBuffer.publish(seq);           // Publish to consumer
            }
        }

        // Wait for the consumer to catch up
        while (handler.sum < (long) (ITERATIONS * (ITERATIONS - 1)) / 2) {
            // simple spin-wait
        }

        long end = System.nanoTime();

        disruptor.shutdown();

        long durationNs = end - start;
        double seconds = durationNs / 1_000_000_000.0;
        double throughput = ITERATIONS / seconds;

        System.out.println("==== DISRUPTOR BENCHMARK ====");
        System.out.printf("Iterations: %,d\n", ITERATIONS);
        System.out.printf("Time: %.3f sec\n", seconds);
        System.out.printf("Throughput: %.2f ops/sec\n", throughput);
        System.out.println("=============================");
    }
}

