package com.javaPlayground.equalsAndHashcode;

import java.util.HashSet;
import java.util.Set;

/*
 * equals() compares field values (name and age), not memory references.
 * hashCode() ensures that equal objects produce the same hash value — vital for hash-based collections.
 * HashSet uses both methods together to prevent duplicate entries.
 */

public class Runner {

    public static void main(String[] args) {

        // --------------------------
        // Example 1: Without equals() and hashCode()
        // --------------------------
        System.out.println("=== Without override equals() and hashCode() methods ===");
        PersonWithoutOverride p1 = new PersonWithoutOverride("John", 23);
        PersonWithoutOverride p2 = new PersonWithoutOverride("John", 23);

        // Even though the data is the same, these are two distinct objects in memory.
        System.out.println("Same hashCode: " + (p1.hashCode() == p2.hashCode())); // likely false
        System.out.println("Equal according to equals(): " + p1.equals(p2));      // false

        Set<PersonWithoutOverride> set1 = new HashSet<>();
        set1.add(p1);
        set1.add(p2);

        // Both objects are stored, because HashSet relies on equals() and hashCode() to detect duplicates.
        System.out.println("HashSet content: " + set1);
        System.out.println("HashSet size: " + set1.size()); // Output: 2

        // --------------------------
        // Example 2: With equals() and hashCode()
        // --------------------------
        System.out.println("\n=== Override equals() and hashCode() methods ===");
        Person person1 = new Person("John", 23);
        Person person2 = new Person("John", 23);

        // Both objects have the same field values, and our overrides make them logically equal.
        System.out.println("Same hashCode: " + (person1.hashCode() == person2.hashCode())); // true
        System.out.println("Equal according to equals(): " + person1.equals(person2));       // true

        Set<Person> set2 = new HashSet<>();
        set2.add(person1);
        set2.add(person2);

        // Only one object is stored because equals() and hashCode() define them as duplicates.
        System.out.println("HashSet content: " + set2);
        System.out.println("HashSet size: " + set2.size()); // Output: 1
    }
}
