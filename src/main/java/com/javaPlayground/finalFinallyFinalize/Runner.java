package com.javaPlayground.finalFinallyFinalize;

import java.util.ArrayList;
import java.util.List;

public class Runner {
    // final example -> cannot be reassigned
    private final int value = 10;
//    value = 20; -> value cannot be reinitialized if its declared as final

    // finally -> found in a try/catch/finally block code
    public void testMethod() {
        try {
            System.out.println("Try block gets executed.");
        } catch(Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            System.out.println("Finally block gets executed regardless of either try or catch gets executed");
        }
    }

    // finalize -> used by garbage collector to clean up unused objects
    public static void main(String[] args) {
        // use of garbage collector
        Runner obj = new Runner();
        obj.testMethod();
        obj= null;
        System.gc();

        // runtime polymorphism and final list can add elements
        ArrayList<String> arr = new ArrayList<>();
        final List<String> list = new ArrayList<>(); // runtime polymorphism
        list.add("hey");
        list.add("sup");
//        System.out.println(list);
    }

    @Override
    protected void finalize(){
        System.out.println("Finalize method gets called to destroy/remove unused objects");
    }
}
