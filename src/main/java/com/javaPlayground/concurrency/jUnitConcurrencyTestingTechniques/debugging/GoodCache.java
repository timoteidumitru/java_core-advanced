package com.javaPlayground.concurrency.jUnitConcurrencyTestingTechniques.debugging;

public class GoodCache {
    private final java.util.concurrent.ConcurrentHashMap<String, String> map =
            new java.util.concurrent.ConcurrentHashMap<>();

    String get(String k) {
        return map.get(k);
    }
}

