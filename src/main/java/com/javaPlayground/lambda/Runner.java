package com.javaPlayground.lambda;

import java.util.Random;
import java.util.function.*;

public class Runner {
    public static void main(String[] args) {
//       Payment pay = (t) -> 500 + t;
//       System.out.println(pay.transfer(300));

        //Function<T, R>: Takes a single argument of type T and returns a result of type R.
        Function<String, String> fun = (t) -> "Hey " + t;
        System.out.println(fun.apply("Tim"));

        //Predicate<T>: Takes a single argument and returns a boolean.
        Predicate<Integer> pre = (pr) -> pr > 100;
//        System.out.println(pre.test(110));

        //Supplier<T>: Returns an object of type T without taking any arguments.
        Supplier<Integer> sup = () -> new Random().nextInt();
//        System.out.println(sup.get());

        //Consumer<T>: Takes a single argument of type T and performs some operation but does not return any result.
        Consumer<String> con = c -> System.out.println("Hey " + c);
//        con.accept("Tim");

        // BiFunction<T, U, R>: Takes two arguments of types T and U, and returns a result of type R
        BiFunction<Integer, Integer, Double> bf = (a, b) -> (double) (a * b);
//        System.out.println(bf.apply(2,3));
    }

}
