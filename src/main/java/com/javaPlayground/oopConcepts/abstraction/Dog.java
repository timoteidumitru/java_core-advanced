package com.javaPlayground.oopConcepts.abstraction;

import lombok.Getter;

@Getter
public class Dog extends Animal{
    String favoriteActivity = "play fetch";

    @Override
    void eat() {
        System.out.println("Dog's diet consist vegetable and meat");
    }

    @Override
    void sound() {
        System.out.println("Dog barks.. woff-woff");
    }

}
