package com.javaPlayground.oopConcepts.inheritance;

public class Runner {
    /*
        - Reuse all the property of a parent class into a child class.
        Use case:
            - In Service or DAO classes.
    */
    public static void main(String[] args) {
        AudiCar audiCar = new AudiCar();
        audiCar.accelerate();
        audiCar.stopped();
        audiCar.refill();
        audiCar.doService();
    }
}
