package com.javaPlayground.concurrency.forkJoin;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;

public class MainBenchmark {

//    Node root = generateRandomTree(5, 70)
//    Parallel:   TreeStats(sum=11927630360, count=23303543, max=1023, height=5) in 10514ms
//    Sequential: TreeStats(sum=11542989141, count=22551544, max=1023, height=5) in 9886ms

//    Node root = generateRandomTree(15, 4)
//    Parallel:   TreeStats(sum=2812084126, count=5495036, max=1023, height=15) in 2619ms
//    Sequential: TreeStats(sum=4347573364, count=8493767, max=1023, height=15) in 3984ms

//    Node root = generateRandomTree(25, 3);
//    Parallel:   TreeStats(sum=37295729839, count=72860422, max=1023, height=25) in 34191ms
//    Sequential: TreeStats(sum=8619808593, count=16840537, max=1023, height=25) in 7778ms

    public static void main(String[] args) {

        System.out.println("Generating WIDE tree...");
        Node root = generateRandomTree(25, 3);

        // Sequential approach
//        SequentialTaskCalculator seq = new SequentialTaskCalculator();
//        long t1 = System.currentTimeMillis();
//        TreeStats seqStats = seq.compute(root);
//        long t2 = System.currentTimeMillis();

//        System.out.println("Sequential: " + seqStats + " in " + (t2 - t1) + "ms");

        // Parallel approach
        ForkJoinPool pool = ForkJoinPool.commonPool();
        long t3 = System.currentTimeMillis();
        TreeStats parStats = pool.invoke(new ParallelTaskCalculator(root));
        long t4 = System.currentTimeMillis();

        System.out.println("Parallel:   " + parStats + " in " + (t4 - t3) + "ms");
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
