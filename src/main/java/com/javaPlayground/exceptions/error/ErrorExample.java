package com.javaPlayground.exceptions.error;

public class ErrorExample {
    public static void main(String[] args) {
        System.out.println("=== Triggering StackOverflowError ===");
        try {
            triggerStackOverflow();
        } catch (StackOverflowError e) {
            System.out.println("Caught an Error: " + e.getClass().getSimpleName());
        } finally {
            System.out.println("StackOverflow recovery attempt finished.\n");
        }

        System.out.println("=== Triggering OutOfMemoryError ===");
        try {
            triggerOutOfMemory();
        } catch (OutOfMemoryError e) {
            System.out.println("Caught an Error: " + e.getClass().getSimpleName());
        } finally {
            System.out.println("OutOfMemory recovery attempt finished.\n");
        }

        System.out.println("=== Triggering Exception ===");
        try {
            triggerException();
        } catch (Exception e) {
            System.out.println("Caught Exception: " + e.getClass().getSimpleName());
        } finally {
            System.out.println("Exception handled successfully.\n");
        }
    }

    static void triggerStackOverflow() {
        // Infinite recursion -> StackOverflowError
        triggerStackOverflow();
    }

    static void triggerOutOfMemory() {
        // Try to allocate a huge array -> OutOfMemoryError
        int[] memoryHog = new int[Integer.MAX_VALUE];
    }

    static void triggerException() throws Exception {
        throw new Exception("This is a checked exception");
    }
}

