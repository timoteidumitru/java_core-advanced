package com.javaPlayground.equalsAndHashcode;

// This version intentionally does NOT override equals() or hashCode()
public class PersonWithoutOverride {
    String name;
    int age;

    public PersonWithoutOverride(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "PersonWithoutOverride{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}

