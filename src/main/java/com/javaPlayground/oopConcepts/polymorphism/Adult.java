package com.javaPlayground.oopConcepts.polymorphism;

public class Adult extends Person implements CanWork, CanStudy{
    @Override
    public void canStudy() {
        System.out.println("An adult person has the ability to study.");
    }

    @Override
    public void canStudy(int studyHours) {
        System.out.println("This person can study " + studyHours + " hours a day.");
    }

    @Override
    public void canWork() {
        System.out.println("An adult person has the ability to work.");
    }

    @Override
    public void canWork(int workHours) {
        System.out.println("This person can work " + workHours + " hours a day.");
    }

    @Override
    public void canSleepAndEat(){
        System.out.println("An adult person can also eat and sleep.");
    }
}
