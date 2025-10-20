package com.javaPlayground.javaIO;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class InputOutputStreams {
    public static void main(String[] args) {
        String inputFile = "src/main/resources/input.txt";
        String outputFile = "src/main/resources/output.txt";

        // InputStreamReader to read from a file
        try (InputStreamReader isr = new InputStreamReader(new FileInputStream(inputFile), StandardCharsets.UTF_8);
             OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(outputFile), StandardCharsets.UTF_8)) {

            int data;
            // Read one character at a time
            while ((data = isr.read()) != -1) {
                // Write the same data to the output file
                osw.write(data);
            }

            System.out.println("Data successfully copied from input file to output file.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
