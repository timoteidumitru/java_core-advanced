package com.javaPlayground.concurrency.testingAndDebuggingTechniques.debugging;

public class GoodCpuAlgorithm {

    public static void main(String[] args) {
        while (true) {
            fibIterative(40);
        }
    }

    static long fibIterative(int n) {
        long a = 0, b = 1;
        for (int i = 0; i < n; i++) {
            long tmp = a + b;
            a = b;
            b = tmp;
        }
        return a;
    }
}

