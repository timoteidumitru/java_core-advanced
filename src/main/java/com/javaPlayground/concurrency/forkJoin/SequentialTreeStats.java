package com.javaPlayground.concurrency.forkJoin;


public class SequentialTreeStats {

    public static TreeStats compute(Node node) {
        long sum = node.getValue();
        long count = 1;
        long max = node.getValue();
        long height = 1;

        for (Node child : node.getChildren()) {
            TreeStats childStats = compute(child);

            sum += childStats.sum;
            count += childStats.count;
            max = Math.max(max, childStats.max);
            height = Math.max(height, childStats.height + 1);
        }

        return new TreeStats(sum, count, max, height);
    }
}

