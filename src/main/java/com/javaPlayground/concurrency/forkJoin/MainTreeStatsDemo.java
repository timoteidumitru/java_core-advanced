package com.javaPlayground.concurrency.forkJoin;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;

public class MainTreeStatsDemo {

    private static final Random random = new Random();

    public static Node randomTree(int depth, int branching) {
        Node root = new Node(random.nextInt(1000));
        if (depth == 0) return root;
        for (int i = 0; i < branching; i++) {
            root.addChild(randomTree(depth - 1, branching));
        }
        return root;
    }

    public static void main(String[] args) {

        Node tree = randomTree(10, 4);  // Large tree

        ForkJoinPool pool = new ForkJoinPool();

        System.out.println("=== Parallel Tree Stats ===");
        long t1 = System.currentTimeMillis();
        TreeStats parallel = pool.invoke(new ParallelTreeStatsTask(tree));
        long t2 = System.currentTimeMillis();
        System.out.println(parallel);
        System.out.println("Parallel time: " + (t2 - t1) + " ms");

        System.out.println("\n=== Sequential Tree Stats ===");
        long t3 = System.currentTimeMillis();
        TreeStats sequential = SequentialTreeStats.compute(tree);
        long t4 = System.currentTimeMillis();
        System.out.println(sequential);
        System.out.println("Sequential time: " + (t4 - t3) + " ms");
    }
}

