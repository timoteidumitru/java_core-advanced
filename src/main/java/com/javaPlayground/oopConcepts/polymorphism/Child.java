package com.javaPlayground.oopConcepts.polymorphism;

public class Child extends Person{

    @Override
    public void canSleepAndEat(){
        System.out.println("A child can sleep and eat.");
    }

    public void canSleepAndEat(int time){
        System.out.println("A child can sleep up to " +time+ " hours a day.");
    }
}
