package com.javaPlayground.functionalInterfaces;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class User {
    // Getters and Setters
    private int id;
    private String name;
    private String email;

    // Constructor
    public User(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    // toString method for easy printing
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}

