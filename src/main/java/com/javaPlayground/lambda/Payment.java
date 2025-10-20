package com.javaPlayground.lambda;

import java.util.Random;

@FunctionalInterface
public interface Payment {
    Integer transfer(Integer amount);

    default double checkBalance(){
        return new Random().nextDouble();
    }

    static double withdraw(double amount){
        return (new Random().nextDouble() * 100) - amount;
    }
}
