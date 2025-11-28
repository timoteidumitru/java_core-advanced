package com.javaPlayground.concurrency.forkJoin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class ParallelTreeStatsTask extends RecursiveTask<TreeStats> {

    private final Node node;

    public ParallelTreeStatsTask(Node node) {
        this.node = node;
    }

    @Override
    protected TreeStats compute() {

        long sum = node.getValue();
        long count = 1;
        long max = node.getValue();
        long height = 1;

        List<ParallelTreeStatsTask> subtasks = new ArrayList<>();

        for (Node child : node.getChildren()) {
            ParallelTreeStatsTask task = new ParallelTreeStatsTask(child);
            task.fork();
            subtasks.add(task);
        }

        for (ParallelTreeStatsTask task : subtasks) {
            TreeStats childStats = task.join();
            sum += childStats.sum;
            count += childStats.count;
            max = Math.max(max, childStats.max);
            height = Math.max(height, childStats.height + 1);
        }

        return new TreeStats(sum, count, max, height);
    }
}

