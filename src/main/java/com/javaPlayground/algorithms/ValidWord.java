package com.javaPlayground.algorithms;

import java.util.ArrayList;
import java.util.List;

public class ValidWord {
    public static void main(String[] args) {
        // A word is valid if:
        // - contains min 3 chars
        // - contains digits 0-9 and lower/upper case letters
        // - at least 1 vowel
        // - at least 1 consonant
        // - input a string and output true or false

        String input = "Hello1";
        System.out.println("Is valid: " + validWord(input));
    }

    private static boolean validWord(String input) {
        // Lists for vowels, consonants, and digits
        List<String> vowels = new ArrayList<>(List.of("a", "e", "i", "o", "u"));
        List<String> consonants = new ArrayList<>(List.of(
                "b", "c", "d", "f", "g", "h", "j", "k", "l", "m",
                "n", "p", "q", "r", "s", "t", "v", "w", "x", "y", "z"
        ));
        List<Integer> numbers = new ArrayList<>(List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));

        // 1️⃣ Check minimum length
        if (input.length() < 3) {
            return false;
        }

        boolean hasVowel = false;
        boolean hasConsonant = false;
        boolean hasDigit = false;
        boolean hasLetter = false;

        // 2️⃣ Loop through each character
        for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);
            String lowerChar = String.valueOf(ch).toLowerCase();

            // Check if it’s a digit
            if (Character.isDigit(ch)) {
                int digit = Character.getNumericValue(ch);
                if (numbers.contains(digit)) {
                    hasDigit = true;
                }
            }

            // Check if it’s a letter
            if (Character.isLetter(ch)) {
                hasLetter = true;

                // Check if vowel or consonant
                if (vowels.contains(lowerChar)) {
                    hasVowel = true;
                } else if (consonants.contains(lowerChar)) {
                    hasConsonant = true;
                }
            }
        }

        // 3️⃣ Final condition
        return hasDigit && hasLetter && hasVowel && hasConsonant;
    }
}
