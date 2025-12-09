package com.javaPlayground.concurrency.concurrencyBestPractices;


public final class ImmutableDesignExample {
    private final String name;
    private final int age;


    public ImmutableDesignExample(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String name() { return name; }
    public int age() { return age; }

    public static void main(String[] args) {
        ImmutableDesignExample profile = new ImmutableDesignExample("John", 30);
        // Can be shared safely across any number of threads
    }
}
