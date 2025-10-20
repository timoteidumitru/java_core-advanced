package com.javaPlayground.oopConcepts.abstraction;

public class Runner {
    /*
        - Hiding implementation of methods and give only the signatures.
        - Abstract methods declared in abstract class should be overridden in the class that extends it.
    */
    public static void main(String[] args) {
        Dog dog = new Dog();
        Wolf wolf = new Wolf();
        GermanShepherd germanShepherd = new GermanShepherd();
        dog.favoriteActivity(dog.getFavoriteActivity());
        dog.sound();
        wolf.favoriteActivity(wolf.getFavoriteActivity());
        wolf.sound();
        germanShepherd.favoriteActivity(germanShepherd.getFavoriteActivity());
        germanShepherd.sound();
    }
}
