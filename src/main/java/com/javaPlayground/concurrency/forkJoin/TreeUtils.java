package com.javaPlayground.concurrency.forkJoin;

import java.util.List;

public class TreeUtils {

    // CPU-heavy work simulation
    public static long heavyWork(long v) {
        long x = v;
        for (int i = 0; i < 500; i++) {
            x = (x * 1664525 + 1013904223) & 0xFFFFFFFFL;
        }
        return x & 1023;
    }

    // Aggregate a list of child TreeStats into parent stats
    public static TreeStats aggregate(long baseValue, List<TreeStats> childStats) {
        long sum = baseValue;
        long count = 1;
        long max = baseValue;
        long maxChildHeight = 0;

        for (TreeStats cs : childStats) {
            sum += cs.getSum();
            count += cs.getCount();
            max = Math.max(max, cs.getMax());
            maxChildHeight = Math.max(maxChildHeight, cs.getHeight());
        }

        long height = maxChildHeight + 1;
        return new TreeStats(sum, count, max, height);
    }
}
