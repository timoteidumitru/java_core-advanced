package com.javaPlayground.oopConcepts.polymorphism;

public class Person implements CanSleepAndEat{
    @Override
    public void canSleepAndEat() {
        System.out.println("This person can sleep and eat.");
    }
}
