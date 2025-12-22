package com.javaPlayground.concurrency.jUnitConcurrencyTestingTechniques.debugging;

//High contention
//Poor scaling
public class BadCache {
    private final java.util.Map<String, String> map = new java.util.HashMap<>();

    synchronized String get(String k) {
        return map.get(k);
    }
}

