package com.javaPlayground.IO_Operations;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ReadFileUsingStreams {
    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Path.of("src/main/resources/IO_operations/financial-report.txt"));

        lines.stream().skip(1).limit(lines.size() - 2)
                .forEach(System.out::println);
    }
}
