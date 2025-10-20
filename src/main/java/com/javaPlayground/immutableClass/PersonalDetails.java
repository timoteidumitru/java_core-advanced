package com.javaPlayground.immutableClass;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PersonalDetails {
    private int id;
    private String name;

    @Override
    public String toString() {
        return "PersonalDetails{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
