package com.javaPlayground.concurrency.locks;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeadlockExample {

    private final String name;

    public synchronized void bow(DeadlockExample bower) {
        System.out.println(this.name + " bows to " + bower.name);
        bower.bowBack(this);
    }

    public synchronized void bowBack(DeadlockExample bower) {
        System.out.println(this.name + " bows back to " + bower.name);
    }

    public static void main(String[] args) {
        DeadlockExample a = new DeadlockExample("Alice");
        DeadlockExample b = new DeadlockExample("Bob");

        new Thread(() -> a.bow(b)).start();
        new Thread(() -> b.bow(a)).start();
    }
}

