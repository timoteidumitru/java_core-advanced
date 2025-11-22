package com.javaPlayground.concurrency.locks;

public class DoubleCheckedLocking {

    private static volatile DoubleCheckedLocking instance;

    public static DoubleCheckedLocking get() {
        if (instance == null) {                             // 1st check (no lock)
            synchronized (DoubleCheckedLocking.class) {
                if (instance == null) {                     // 2nd check
                    instance = new DoubleCheckedLocking();
                }
            }
        }
        return instance;
    }
}
