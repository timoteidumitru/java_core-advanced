package com.javaPlayground.oopConcepts.abstraction;

import lombok.Getter;

@Getter
public class GermanShepherd extends Animal {
    String favoriteActivity = "long walks";

    @Override
    void eat() {
        System.out.println("GermanShepherd's diet consist meat and vegetables.");
    }

    @Override
    void sound() {
        System.out.println("GermanShepherd howls and bark.");
    }

}
