package com.javaPlayground.javaIO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class UserInteraction {
    public static void main(String[] args) throws IOException {
        // simple use of buffer reader
        simpleUserInteraction();
    }

    public static void simpleUserInteraction() throws IOException {
        while (true){
            System.out.println("\nEnter your input to be displayed:");

            InputStreamReader input = new InputStreamReader(System.in);
            BufferedReader reader = new BufferedReader(input);
            String userInput = reader.readLine();

            System.out.println("Your input: " + userInput);
        }
    }

}
