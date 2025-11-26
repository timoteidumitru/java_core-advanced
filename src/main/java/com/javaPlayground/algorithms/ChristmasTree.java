package com.javaPlayground.algorithms;

public class ChristmasTree {

    public static String buildTree(int a) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i <= a; i++) {

            if (i == 0) {
                sb.append(" ".repeat(a)).append("^").append("\n");

            } else if (i > 2 && i < a) {
                StringBuilder line = new StringBuilder();
                for (int j = 0; j <= i * 2; j++) {
                    int rand = (int) (Math.random() * 2);
                    String out = rand > 0 ? "o" : "*";
                    line.append(out);
                }
                sb.append(" ".repeat(a - i)).append(line).append("\n");

            } else {
                // fixed branch width here
                sb.append(" ".repeat(a - i)).append("*".repeat(i * 2 + 1)).append("\n");
            }
        }

        sb.append(" ".repeat(a - 1)).append("#");
        return sb.toString();
    }

    public static void main(String[] args) throws InterruptedException {
        String previous = "";

        for (int t = 0; t < 10; t++) {
            String tree = buildTree(14);

            System.out.print("\r" + previous.replaceAll(".", " "));
            System.out.print("\r" + tree);

            previous = tree;
            Thread.sleep(1000);
        }
    }
}

