package com.javaPlayground.concurrency.forkJoin;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;

public class MainBenchmark {

    // Wide Tree
//    Node root = generateRandomTree(4, 250)
//    Parallel:   TreeStats(sum=7901824548, count=15436378, max=1023, height=4) in 1268ms
//    Sequential: TreeStats(sum=7996346160, count=15623107, max=1023, height=4) in 7113ms

    // Medium Tree
//    Node root = generateRandomTree(15, 4)
//    Parallel:   TreeStats(sum=5724133840, count=11182757, max=1023, height=15) in 5418ms
//    Sequential: TreeStats(sum=4890379358, count=9554080, max=1023, height=15) in 4614ms

    // Deep Tree
//    Node root = generateRandomTree(25, 3);
//    Parallel:   TreeStats(sum=33638991050, count=65721031, max=1023, height=25) in 31221ms
//    Sequential: TreeStats(sum=9008672288, count=17601312, max=1023, height=25) in 8326ms

    public static void main(String[] args) {

        System.out.println("Generating tree...");
        Node root = generateRandomTree(15, 4);

        // Parallel approach
//        ForkJoinPool pool = ForkJoinPool.commonPool();
//        long t3 = System.currentTimeMillis();
//        TreeStats parStats = pool.invoke(new ParallelTaskCalculator(root));
//        long t4 = System.currentTimeMillis();
//
//        System.out.println("Parallel:   " + parStats + " in " + (t4 - t3) + "ms");

        // Sequential approach
        SequentialTaskCalculator seq = new SequentialTaskCalculator();
        long t1 = System.currentTimeMillis();
        TreeStats seqStats = seq.compute(root);
        long t2 = System.currentTimeMillis();

        System.out.println("Sequential: " + seqStats + " in " + (t2 - t1) + "ms");
    }

    private static Node generateRandomTree(int height, int branchingFactor) {
        Random rand = new Random();
        return generateRec(1, height, branchingFactor, rand);
    }

    private static Node generateRec(int level, int maxHeight, int branching, Random rand) {
        Node node = new Node(rand.nextInt(1000));

        if (level < maxHeight) {
            // Wide tree: mostly branchingFactor children
            int children = branching - 3 + rand.nextInt(5);
            for (int i = 0; i < children; i++) {
                node.addChild(generateRec(level + 1, maxHeight, branching, rand));
            }
        }
        return node;
    }
}
