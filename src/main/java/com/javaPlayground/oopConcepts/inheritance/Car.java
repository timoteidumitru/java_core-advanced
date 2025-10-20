package com.javaPlayground.oopConcepts.inheritance;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Car implements Service {
    private String make;
    private String color;
    private int enginePower;
    private int mileage;

    public void refill(){
        System.out.println("Your tank is now full.");
    }

    public void accelerate(){
        System.out.println("Car start to accelerate.. vroom-vroom.");
    }

    public void stopped(){
        System.out.println("Car stopped.");
    }

    @Override
    public void doService() {
        System.out.println("Your car repository is due next month.");
    }
}
