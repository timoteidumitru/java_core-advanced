package com.javaPlayground.javaIO;

import org.springframework.core.io.ClassPathResource;
import java.io.*;

/*
* Byte streams are used to perform input and output of raw binary data (8-bit bytes).
* They are ideal for images, audio/video, PDFs, and any non-text files.
*/

public class ByteStreamExample {
    public static void main(String[] args) {
        try {
            // Load input file from resources (inside the classpath)
            ClassPathResource inputResource = new ClassPathResource("IO_operations/input/input.jpg");

            // Define a safe output directory (outside the JAR)
            File outputDir = new File("src/main/resources/IO_operations/output");
            if (!outputDir.exists()) {
                outputDir.mkdirs(); // create if missing
            }

            // Define output file location
            File outputFile = new File(outputDir, "copy-" + inputResource.getFilename());

            // Copy the file using buffered byte streams
            try (
                    InputStream fis = inputResource.getInputStream();
                    OutputStream fos = new BufferedOutputStream(new FileOutputStream(outputFile))
            ) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = fis.read(buffer)) != -1) {
                    fos.write(buffer, 0, bytesRead);
                }
                System.out.println("✅ File copied successfully to IO_operations folder named: " + outputFile.getName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
