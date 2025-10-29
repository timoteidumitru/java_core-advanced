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
        System.out.println("User stopped by gas station for refill.");
        System.out.println("Your tank is now full.");
    }

    public void accelerate(){
        System.out.println("User starts to press acceleration pedal.");
        System.out.println("Car start to accelerate.. vroom-vroom.");
    }

    public void stopped(){
        System.out.println("User started to press break pedal.");
        System.out.println("Car stopped slowly.");
    }

    @Override
    public void doService() {
        System.out.println("User checking his car service book.");
        System.out.println("It shows that car service is due next month.");
    }
}
