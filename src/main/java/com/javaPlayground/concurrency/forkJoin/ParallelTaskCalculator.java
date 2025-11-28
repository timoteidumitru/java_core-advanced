package com.javaPlayground.concurrency.forkJoin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class ParallelTaskCalculator extends RecursiveTask<TreeStats> {

    private static final int THRESHOLD = 200; // batch children before splitting
    private final Node node;

    public ParallelTaskCalculator(Node node) {
        this.node = node;
    }

    @Override
    protected TreeStats compute() {

        // CPU-heavy task
        long baseValue = heavyWork(node.getValue());

        long sum = baseValue;
        long count = 1;
        long max = baseValue;
        long height = 1;

        List<Node> children = node.getChildren();

        // Sequential fallback for small batch
        if (children.size() < THRESHOLD) {
            long maxChildHeight = 0;

            for (Node child : children) {
                TreeStats c = new ParallelTaskCalculator(child).compute();

                sum += c.getSum();
                count += c.getCount();
                max = Math.max(max, c.getMax());
                maxChildHeight = Math.max(maxChildHeight, c.getHeight());
            }

            height = maxChildHeight + 1;
            return new TreeStats(sum, count, max, height);
        }

        // Real parallel execution
        List<ParallelTaskCalculator> tasks = new ArrayList<>();

        for (Node child : children) {
            ParallelTaskCalculator task = new ParallelTaskCalculator(child);
            task.fork();
            tasks.add(task);
        }

        long maxChildHeight = 0;

        for (ParallelTaskCalculator t : tasks) {
            TreeStats c = t.join();
            sum += c.getSum();
            count += c.getCount();
            max = Math.max(max, c.getMax());
            maxChildHeight = Math.max(maxChildHeight, c.getHeight());
        }

        height = maxChildHeight + 1;
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
