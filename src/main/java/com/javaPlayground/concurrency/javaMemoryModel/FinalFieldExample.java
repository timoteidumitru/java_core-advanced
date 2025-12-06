package com.javaPlayground.concurrency.javaMemoryModel;

public class FinalFieldExample {
    static class Immutable {
        final int a;
        int b; // non-final
        Immutable(int a, int b) { this.a = a; this.b = b; }
    }

    static Immutable ref;

    public static void main(String[] args) throws Exception {
        // a (final) will be visible as 42 once ref is observed.
        // b may be 0 (stale) on some platforms if not safely published.

        Thread writer = new Thread(() -> ref = new Immutable(42, 99));
        Thread reader = new Thread(() -> {
            Immutable r;
            while ((r = ref) == null) Thread.yield();
            System.out.println("a=" + r.a + " b=" + r.b);
        });

        writer.start(); reader.start();
        writer.join(); reader.join();
    }
}

