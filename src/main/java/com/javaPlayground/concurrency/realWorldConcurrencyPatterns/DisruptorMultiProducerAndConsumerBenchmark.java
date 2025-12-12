package com.javaPlayground.concurrency.realWorldConcurrencyPatterns;

import com.lmax.disruptor.BusySpinWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.WorkHandler;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DisruptorMultiProducerAndConsumerBenchmark {

    static final int ITERATIONS = 20_000_000;
    static final int PRODUCER_THREADS = 4;
    static final int CONSUMER_THREADS = 4;

    public static class LongEvent {
        long value;
        public void set(long v) { value = v; }
    }

    public static class LongEventFactory implements EventFactory<LongEvent> {
        @Override public LongEvent newInstance() { return new LongEvent(); }
    }

    public static class LongEventHandler implements WorkHandler<LongEvent> {
        long sum;
        @Override public void onEvent(LongEvent event) {
            sum += event.value;
        }
    }

    public static void main(String[] args) throws Exception {

        int bufferSize = 1024 * 8;

        ExecutorService executor = Executors.newFixedThreadPool(CONSUMER_THREADS);

        Disruptor<LongEvent> disruptor = new Disruptor<>(
                new LongEventFactory(),
                bufferSize,
                Executors.defaultThreadFactory(),
                ProducerType.MULTI,                 // Multi-producer mode
                new BusySpinWaitStrategy()
        );

        LongEventHandler[] handlers = new LongEventHandler[CONSUMER_THREADS];
        for (int i = 0; i < CONSUMER_THREADS; i++)
            handlers[i] = new LongEventHandler();

        disruptor.handleEventsWithWorkerPool(handlers);

        RingBuffer<LongEvent> ringBuffer = disruptor.start();

        long start = System.nanoTime();

        Runnable publisher = () -> {
            for (int i = 0; i < ITERATIONS / PRODUCER_THREADS; i++) {
                long seq = ringBuffer.next();
                try {
                    ringBuffer.get(seq).set(i);
                } finally {
                    ringBuffer.publish(seq);
                }
            }
        };

        Thread[] producers = new Thread[PRODUCER_THREADS];
        for (int i = 0; i < PRODUCER_THREADS; i++) {
            producers[i] = new Thread(publisher);
            producers[i].start();
        }

        for (Thread t : producers) t.join();

        disruptor.shutdown();
        executor.shutdown();

        long end = System.nanoTime();

        double duration = (end - start) / 1_000_000_000.0;
        double throughput = ITERATIONS / duration;

        System.out.println("==== MULTI PRODUCER-CONSUMER DISRUPTOR ====");
        System.out.printf("Producers: %d   Consumers: %d\n", PRODUCER_THREADS, CONSUMER_THREADS);
        System.out.printf("Time: %.3f sec\n", duration);
        System.out.printf("Throughput: %.2f ops/sec\n", throughput);
        System.out.println("==================================");
    }

}
