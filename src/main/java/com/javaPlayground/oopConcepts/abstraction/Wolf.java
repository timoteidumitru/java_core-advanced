package com.javaPlayground.oopConcepts.abstraction;

import lombok.Getter;

@Getter
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

}
