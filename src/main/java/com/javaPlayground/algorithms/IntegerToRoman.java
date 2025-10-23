package com.javaPlayground.algorithms;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class IntegerToRoman {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        while (true) {
            System.out.print("Please enter a number to be converted to Roman: ");

            String line = input.nextLine();

            try {
                int number = Integer.parseInt(line);
                convertIntegerToRoman(number);
            } catch (NumberFormatException e) {
                System.out.println("Your input was not a number, please try again.\n");
            }
        }
    }

    private static void convertIntegerToRoman(int input) {

        Map<Integer, String> data = new HashMap<>() {{
            put(1, "I");
            put(5, "V");
            put(10, "X");
        }};

        StringBuilder result = new StringBuilder();

        // number 10
        if (input == 10) {
            result.append(data.get(10));
        }

        // number 9
        if (input == 9) {
            result.append(data.get(1));
            result.append(data.get(10));
        }

        // 5 to 8 numbers
        if (input >= 5 && input <= 8) {
            result.append(data.get(5));
            for (int i = 0; i < input - 5; i++) {
                result.append(data.get(1));
            }
        }

        // number 4
        if (input == 4) {
            result.append(data.get(1));
            result.append(data.get(5));
        }

        // 1 to 3 numbers
        if (input >= 1 && input <= 3) {
            for (int i = 0; i < input; i++) {
                result.append(data.get(1));
            }
        }


        System.out.print("Conversion to Roman is: " + result + "\n");
    }
}

