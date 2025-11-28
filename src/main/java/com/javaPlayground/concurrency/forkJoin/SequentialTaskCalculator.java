package com.javaPlayground.concurrency.forkJoin;

public class SequentialTaskCalculator {

    public TreeStats compute(Node node) {
        long baseValue = heavyWork(node.getValue());

        long sum = baseValue;
        long count = 1;
        long max = baseValue;

        long maxChildHeight = 0;

        for (Node child : node.getChildren()) {
            TreeStats c = compute(child);
            sum += c.getSum();
            count += c.getCount();
            max = Math.max(max, c.getMax());
            maxChildHeight = Math.max(maxChildHeight, c.getHeight());
        }

        long height = maxChildHeight + 1;
        return new TreeStats(sum, count, max, height);
    }

    private static long heavyWork(long v) {
        long x = v;
        for (int i = 0; i < 500; i++) {
            x = (x * 1664525 + 1013904223) & 0xFFFFFFFFL;
        }
        return x & 1023;
    }
}
