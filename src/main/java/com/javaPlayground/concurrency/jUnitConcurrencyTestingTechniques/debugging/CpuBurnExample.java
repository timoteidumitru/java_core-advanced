package com.javaPlayground.concurrency.jUnitConcurrencyTestingTechniques.debugging;

public class CpuBurnExample {

    public static void main(String[] args) {
        while (true) {
            fibonacci(10);
        }
    }

    static long fibonacci(long n) {
        System.out.println(STR."N number is: \{n}");
        return n <= 1 ? n : fibonacci(n - 1) + fibonacci(n - 2);
    }
}

