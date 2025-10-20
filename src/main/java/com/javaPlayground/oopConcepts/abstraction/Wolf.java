package com.javaPlayground.oopConcepts.abstraction;

public class Wolf extends Animal{
    String favoriteActivity = "pack hunting";

    @Override
    void eat() {
        System.out.println("Wolf prefer to eat raw meat.");
    }

    @Override
    void sound() {
        System.out.println("Wolf howls.");
    }

    public String getFavoriteActivity() {
        return favoriteActivity;
    }
}
