package com.javaPlayground.concurrency.forkJoin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class ParallelTaskCalculator extends RecursiveTask<TreeStats> {

    private static final int THRESHOLD = 200; // batch children before parallel execution
    private final Node node;

    public ParallelTaskCalculator(Node node) {
        this.node = node;
    }

    @Override
    protected TreeStats compute() {

        long baseValue = TreeUtils.heavyWork(node.getValue());
        List<Node> children = node.getChildren();

        // Sequential fallback if few children
        if (children.size() < THRESHOLD) {
            List<TreeStats> childStats = new ArrayList<>();
            for (Node child : children) {
                childStats.add(new ParallelTaskCalculator(child).compute());
            }
            return TreeUtils.aggregate(baseValue, childStats);
        }

        // Parallel execution
        List<ParallelTaskCalculator> tasks = new ArrayList<>();
        for (Node child : children) {
            ParallelTaskCalculator task = new ParallelTaskCalculator(child);
            task.fork();
            tasks.add(task);
        }

        List<TreeStats> childStats = new ArrayList<>();
        for (ParallelTaskCalculator t : tasks) {
            childStats.add(t.join());
        }

        return TreeUtils.aggregate(baseValue, childStats);
    }
}
