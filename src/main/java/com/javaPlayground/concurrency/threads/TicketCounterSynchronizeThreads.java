package com.javaPlayground.concurrency.threads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TicketCounterSynchronizeThreads {
    private int seats;


    public synchronized void book(String user, int count) {
        System.out.println(user + " attempting to book " + count + " seats");

        if (count <= seats) {
            System.out.println(user + " booking approved");
            try { Thread.sleep(500); } catch (InterruptedException ignored) {}

            seats -= count;
            System.out.println(user + " successfully booked! Remaining: " + seats);
        }
        else {
            System.out.println(user + " booking failed. Only " + seats + " seats left.");
        }
    }

    public static void main(String[] args) {
        TicketCounterSynchronizeThreads counter = new TicketCounterSynchronizeThreads(10);

        Thread u1 = new Thread(() -> counter.book("User1", 4));
        Thread u2 = new Thread(() -> counter.book("User2", 5));
        Thread u3 = new Thread(() -> counter.book("User3", 3));

        u1.start();
        u2.start();
        u3.start();
    }
}



