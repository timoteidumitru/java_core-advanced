package com.javaPlayground.concurrency.semaphoreAndResourceControl;

import java.util.concurrent.Semaphore;

public class ConnectionPoolExample {

    private final Semaphore available = new Semaphore(10);
    private final Connection[] pool = new Connection[10];
    private final boolean[] used = new boolean[10];

    public ConnectionPoolExample() {
        for (int i = 0; i < 10; i++) {
            pool[i] = new Connection(i);
        }
    }

    public Connection acquire() throws InterruptedException {
        available.acquire();
        return getConnectionFromPool();
    }

    private synchronized Connection getConnectionFromPool() {
        for (int i = 0; i < 10; i++) {
            if (!used[i]) {
                used[i] = true;
                return pool[i];
            }
        }
        throw new RuntimeException("Should never happen");
    }

    public void release(Connection c) {
        synchronized (this) {
            used[c.id] = false;
        }
        available.release();
    }

    public static class Connection {
        public final int id;
        Connection(int id) { this.id = id; }
    }

    public static void main(String[] args) {
        ConnectionPoolExample pool = new ConnectionPoolExample();

        for (int i = 1; i <= 30; i++) {
            int id = i;
            new Thread(() -> {
                try {
                    var conn = pool.acquire();
                    System.out.println("Thread " + id + " using connection " + conn.id);
                    Thread.sleep(1000);
                    pool.release(conn);
                } catch (Exception e) {
                }
            }).start();
        }
    }
}

