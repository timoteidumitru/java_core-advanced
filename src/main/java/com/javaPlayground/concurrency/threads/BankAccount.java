package com.javaPlayground.concurrency.threads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankAccount {
    private int balance;

    public synchronized void withdraw(int amount, String atmName) {
        System.out.println(" - " +atmName + " trying to withdraw " + amount);

        if (balance < amount) {
            System.out.println(atmName + " insufficient funds. Current balance: " + balance);
            return;
        }

        try { Thread.sleep(500); } catch (InterruptedException ignored) {}

        balance -= amount;
        System.out.println(atmName + " successfully withdrew " + amount +
                " | Remaining balance: " + balance);
    }

    public static void main(String[] args) {
        BankAccount account = new BankAccount(1000);
        System.out.println("   -----===== Current user balance: " + account.getBalance() + " =====-----\n");

        Thread atm1 = new Thread(() -> account.withdraw(600, "ATM-1"));
        Thread atm2 = new Thread(() -> account.withdraw(700, "ATM-2"));
        Thread atm3 = new Thread(() -> account.withdraw(200, "ATM-3"));

        atm1.start();
        atm2.start();
        atm3.start();

    }
}


