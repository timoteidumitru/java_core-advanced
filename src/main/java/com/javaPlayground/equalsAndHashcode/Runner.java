package com.javaPlayground.equalsAndHashcode;

import java.util.HashSet;
import java.util.Set;

public class Runner {
    public static void main(String[] args) {
        Person person1 = new Person("John", 23);
        Person person2 = new Person("John", 23);

        System.out.println("Have the same hashcode(): " + (person1.hashCode() == person2.hashCode()));
        System.out.println("Have the same equals(): " + (person1.equals(person2)));

        Set<Person> people = new HashSet<>();
        people.add(person1);
        people.add(person2);

        System.out.println(people);
    }
}
