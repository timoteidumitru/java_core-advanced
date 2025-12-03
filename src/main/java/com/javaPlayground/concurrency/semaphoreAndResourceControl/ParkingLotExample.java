package com.javaPlayground.concurrency.semaphoreAndResourceControl;

import java.util.concurrent.Semaphore;

public class ParkingLotExample {

    private static final Semaphore spaces = new Semaphore(3);

    static class Car extends Thread {
        private final int id;

        public Car(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            try {
                System.out.println("Car " + id + " trying to park...");
                spaces.acquire();
                System.out.println("Car " + id + " parked!");

                Thread.sleep(2000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                spaces.release();
                System.out.println("Car " + id + " left the parking lot.");
            }
        }
    }

    public static void main(String[] args) {
        for (int i = 1; i <= 10; i++) {
            new Car(i).start();
        }
    }
}

