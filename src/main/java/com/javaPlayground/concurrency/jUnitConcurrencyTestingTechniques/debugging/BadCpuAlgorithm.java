package com.javaPlayground.concurrency.jUnitConcurrencyTestingTechniques.debugging;

public class BadCpuAlgorithm {

    public static void main(String[] args) {
        while (true) {
            fib(40);
        }
    }

    static long fib(long n) {
        return n <= 1 ? n : fib(n - 1) + fib(n - 2);
    }
}

