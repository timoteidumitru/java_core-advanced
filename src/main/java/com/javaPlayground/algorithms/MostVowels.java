package com.javaPlayground.algorithms;

public class MostVowels {
    public static void main(String[] args) {
        String str = "Let's find out the words with the most vowels in this string!";
        String vowels = "AEIOUaeiou";

        findWordWithMostVowels(str, vowels);
    }

    private static void findWordWithMostVowels(String str, String vowels) {
        String[] words = str.split(" ");
        int maxVowelsCount = 0;
        StringBuilder result = new StringBuilder();

        for (String word : words) {
            int count = 0;
            for (char c : word.toCharArray()) {
                if (vowels.indexOf(c) >= 0) {
                    count++;
                }
            }

            if (count > maxVowelsCount) {
                maxVowelsCount = count;
                result = new StringBuilder(word);
            } else if (count == maxVowelsCount) {
                result.append(" ").append(word);
            }
        }

        System.out.println("Given data: " + str);
        System.out.println("Words with most vowels: " + result);
    }
}
