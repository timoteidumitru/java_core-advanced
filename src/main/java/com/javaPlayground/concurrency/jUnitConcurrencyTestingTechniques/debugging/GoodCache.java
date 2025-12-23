package com.javaPlayground.concurrency.jUnitConcurrencyTestingTechniques.debugging;

import java.util.concurrent.ConcurrentHashMap;

public class GoodCache {
    private final ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();

    String get(String k) {
        return map.get(k);
    }
}

