package com.javaPlayground.IO_Operations;

import java.io.*;
import java.util.Scanner;

public class FileDataInteraction {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String userChoice;

        File readFile = new File("src/main/resources/IO_operations/write-file.txt");
        File writeFile = new File("src/main/resources/IO_operations/write-file.txt");

        while (true) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Write to file");
            System.out.println("2. Read from file");
            System.out.println("3. Exit");

            userChoice = scanner.nextLine();

            switch (userChoice) {
                case "1":
                    // Write to a file
                    System.out.println("Enter text to write to the file:");
                    String dataToWrite = scanner.nextLine();
                    writeToFile(writeFile, dataToWrite);
                    break;
                case "2":
                    // Read from a file
                    readFromFile(readFile);
                    break;
                case "3":
                    // Exit
                    System.out.println("Exiting...");
                    System.exit(0);
                default:
                    System.out.println("Invalid option, please try again.");
            }
        }
    }

    public static void writeToFile(File writeFile, String data) {
        try (FileWriter writer = new FileWriter(writeFile, true)) {
            // Append data to the file
            writer.write(data + "\n");
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }
    }

    public static void readFromFile(File readFile) {
        try (Scanner reader = new Scanner(readFile)) {
            System.out.println("File content: ");
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                System.out.println(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred while reading the file.");
            e.printStackTrace();
        }
    }
}
