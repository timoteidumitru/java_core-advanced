package com.javaPlayground.IO_Operations;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class CopyDataUsingStreams {
    public static void main(String[] args) {
        String inputFile = "src/main/resources/IO_operations/input.txt";
        String outputFile = "src/main/resources/IO_operations/output.txt";

        // InputStreamReader to read from a file
        try (InputStreamReader isr = new InputStreamReader(new FileInputStream(inputFile), StandardCharsets.UTF_8);
             OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(outputFile), StandardCharsets.UTF_8)) {

            int data;
            // Read one character at a time
            while ((data = isr.read()) != -1) {
                // Write the same data to the output file
                osw.write(data);
            }

            System.out.println("Data successfully copied from input.txt file to output.txt files.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
