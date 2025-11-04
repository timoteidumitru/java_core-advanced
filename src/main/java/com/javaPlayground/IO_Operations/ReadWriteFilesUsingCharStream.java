package com.javaPlayground.IO_Operations;

import java.io.*;
import java.util.Scanner;

public class ReadWriteFilesUsingCharStream {
    public static void main(String[] args) throws IOException {
        File readFile = new File("src/main/resources/IO_operations/read/read-file.txt");
        File writeFile = new File("src/main/resources/IO_operations/write/write-file.txt");
        // simple example reading / writing from/to a file
//        readFromFile(readFile);
//        writeToFile(writeFile);

        // Try with resources example
        readFile(readFile);
        writeFile(writeFile);
    }

    public static void readFromFile(File readFile) {
        try {
            // Create a Scanner object to read the file
            Scanner reader = new Scanner(readFile);

            // Read the file line by line
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                System.out.println(line);
            }

            // Close the reader
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred while reading the file.");
            e.printStackTrace();
        }
    }

    public static void writeToFile(File writeFile) {
        String data = "This is an example of writing to a file in Java.\n";

        try {
            // Create a FileWriter object to write data to a file
            FileWriter writer = new FileWriter(writeFile);
            // Write data to the file
            writer.write(data);
            // Close the writer to save the file
            writer.close();
            System.out.println("Yay, successfully wrote to the file.");
            Scanner sc = new Scanner(writeFile);
            System.out.println(sc.nextLine());
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }
    }

    // Try with Resources example
    public static void readFile(File readFile) {
        // Try-with-resources ensures the Scanner is automatically closed after use
        try (Scanner reader = new Scanner(readFile)) {
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                System.out.println(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred while reading the file.");
            e.printStackTrace();
        }
    }

    public static void writeFile(File writeFile) throws FileNotFoundException {
        String data = "This is an example of writing to a file using try-with-resources in Java.\n";
        // Try-with-resources ensures the FileWriter is automatically closed after use
        try (FileWriter writer = new FileWriter(writeFile)) {
            writer.write(data);
            System.out.println("Yay, successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }
        // after auto close of writer we can read the file we wrote to
        Scanner sc = new Scanner(writeFile);
        System.out.println(sc.nextLine());
    }

}
