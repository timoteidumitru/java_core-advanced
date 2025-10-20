package com.javaPlayground.algorithms;

public class ConvertToTitleCase {
    public static void main(String[] args) {
        String input = "Hey, lets convert this string to a title case using java approach.";

        System.out.println(toTitleCase(input));
    }

    static String toTitleCase(String input) {
        StringBuilder result = new StringBuilder();
        boolean capitalizeNext = true; // Flag to capitalize the next letter

        for (char ch : input.toCharArray()) {
            if (Character.isLetter(ch)) {
                // Capitalize first letter of the word, lowercase the rest
                result.append(capitalizeNext ? Character.toUpperCase(ch) : Character.toLowerCase(ch));
                capitalizeNext = false; // Reset flag after first letter
            } else {
                result.append(ch);
                capitalizeNext = true; // Set flag when encountering a space or punctuation
            }
        }

        return result.toString();
    }
}
