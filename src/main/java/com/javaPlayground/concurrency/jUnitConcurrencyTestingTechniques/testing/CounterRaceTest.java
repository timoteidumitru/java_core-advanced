package com.javaPlayground.concurrency.jUnitConcurrencyTestingTechniques.testing;

import org.junit.Test;
import java.util.concurrent.CountDownLatch;
import static org.junit.Assert.assertEquals;

public class CounterRaceTest {

    int counter = 0;

    @Test
    public void raceConditionDetected() throws Exception {
        int threads = 10;
        CountDownLatch start = new CountDownLatch(1);
        CountDownLatch done = new CountDownLatch(threads);

        for (int i = 0; i < threads; i++) {
            new Thread(() -> {
                try {
                    start.await();
                    counter++; // NOT thread-safe
                } catch (InterruptedException ignored) {
                } finally {
                    done.countDown();
                }
            }).start();
        }

        start.countDown();
        done.await();

        // This test may FAIL intermittently
        assertEquals(threads, counter);
    }
}

