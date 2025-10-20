package com.javaPlayground.algorithms;

import java.util.HashMap;
import java.util.Map;

public class FindOccurrencesInAMap {
    public static void main(String[] args) {
        getOccurrenceFromString();
    }
    public static void getOccurrenceFromString(){
        String str = "Let's find how many occurrences can be found for each letter in this string.";
        Map<Character, Integer> occur = new HashMap<>();
        String filteredStr = str.replaceAll("[^a-zA-Z]", "").toLowerCase();

        for (int i = 0; i < filteredStr.length()-1; i++) {
            char ch = filteredStr.charAt(i);
            if (occur.containsKey(ch)){
                occur.put(ch, occur.get(ch) + 1);
            } else {
                occur.put(ch, 1);
            }
        }

        System.out.println("Output: " + occur);
    }

}
