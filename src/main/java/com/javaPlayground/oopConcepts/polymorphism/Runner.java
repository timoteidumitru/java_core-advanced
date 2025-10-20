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
        adultPerson.canStudy();
        adultPerson.canStudy(2);
        adultPerson.canWork(4);
        adultPerson.canWork();
        childPerson.canSleepAndEat();
        adultPerson.canSleepAndEat();

        // runtime polymorphism
        Person adult = new Person();
        Person child = new Child();
//        child.canSleepAndEat();
//        adult.canSleepAndEat();
    }

}
