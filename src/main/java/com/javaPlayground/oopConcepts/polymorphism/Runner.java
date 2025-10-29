package com.javaPlayground.oopConcepts.polymorphism;

public class Runner {
    /*
        - Single entity with multiple implementations
        Use case:
            - When Overloading or Overriding methods, with Interfaces in general.
    */
    public static void main(String[] args) {
        Adult adultPerson = new Adult();
        Child childPerson = new Child();

        System.out.println("#############################################################");
        System.out.println("Adult person study case:");
        adultPerson.canStudy();
        adultPerson.canStudy(4);
        System.out.println("#############################################################");

        System.out.println("Adult person working case:");
        adultPerson.canWork();
        adultPerson.canWork(10);
        System.out.println("#############################################################");

        System.out.println("A child case:");
        childPerson.canSleepAndEat();
        childPerson.canSleepAndEat(12);
        System.out.println("#############################################################");

        System.out.println("Adult person sleeping case:");
        adultPerson.canSleepAndEat();
        adultPerson.canSleepAndEat(7);
        System.out.println("#############################################################");

        // runtime polymorphism
//        Person adult = new Person();
//        Person child = new Child();
//        child.canSleepAndEat();
//        adult.canSleepAndEat();
    }

}
