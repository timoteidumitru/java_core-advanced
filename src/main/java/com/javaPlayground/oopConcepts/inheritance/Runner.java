package com.javaPlayground.oopConcepts.inheritance;

public class Runner {
    /*
        - Reuse all the property of a parent class into a child class.
        Use case:
            - In Service or DAO classes.
    */
    public static void main(String[] args) {
        AudiCar audiCar = new AudiCar();

        System.out.println("############################################################");
        audiCar.accelerate();
        System.out.println("############################################################");

        audiCar.stopped();
        System.out.println("############################################################");

        audiCar.refill();
        System.out.println("############################################################");

        audiCar.doService();
        System.out.println("############################################################");

    }
}
