package com.javaPlayground.concurrency.javaMemoryModel;

public class DoubleCheckedLockingExample {

        private static volatile DoubleCheckedLockingExample instance;

        private DoubleCheckedLockingExample() {}

        public static DoubleCheckedLockingExample getInstance() {
            DoubleCheckedLockingExample result = instance;
            if (result == null) {
                synchronized (DoubleCheckedLockingExample.class) {
                    result = instance;
                    if (result == null) {
                        instance = result = new DoubleCheckedLockingExample();
                    }
                }
            }
            return result;
        }

}
