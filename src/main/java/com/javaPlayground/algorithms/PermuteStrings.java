package com.javaPlayground.algorithms;

public class PermuteStrings {
    public static void main(String[] args) {
        String input = "ABC";
        char[] chars = input.toCharArray();
        int right = chars.length;

        permuteString(chars, 0, right);
    }

    private static void permuteString(char[] chars, int left, int right) {
        if (left == right) {
            StringBuilder res = new StringBuilder();
            for (char c : chars){
                res.append(c).append(" ");
            }
            System.out.println(res);
        } else {
            for (int i = left; i < right; i++) {
                swapChars(chars, left, i);

                permuteString(chars, left + 1, right);

                swapChars(chars, left, i);
            }
        }
    }

    private static void swapChars(char[] array, int a, int b) {
        char temp = array[a];
        array[a] = array[b];
        array[b] = temp;
    }

}
