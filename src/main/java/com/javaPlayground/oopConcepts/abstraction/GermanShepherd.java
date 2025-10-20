package com.javaPlayground.oopConcepts.abstraction;

public class GermanShepherd extends Animal {
    String favoriteActivity = "long walks";

    @Override
    void eat() {
        System.out.println("GermanShepherd's diet consist meat and vegetables.");
    }

    @Override
    void sound() {
        System.out.println("GermanShepherd howl and bark.");
    }

    public String getFavoriteActivity() {
        return favoriteActivity;
    }
}
