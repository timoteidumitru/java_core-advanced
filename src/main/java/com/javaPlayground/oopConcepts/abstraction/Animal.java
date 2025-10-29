package com.javaPlayground.oopConcepts.abstraction;

abstract class Animal {

    abstract void eat();
    abstract void sound();

    void favoriteActivity(String fav){
        System.out.println("This animal enjoy: " + fav);
    }
}
