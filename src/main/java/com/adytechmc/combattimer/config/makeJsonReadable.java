package com.adytechmc.combattimer.config;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class makeJsonReadable {
    public static void addEmptyLineBetweenJSONArguments(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(filePath + "_temp"))) {

            String line;
            boolean insideQuotes = false;
            boolean afterComma = false;

            while ((line = reader.readLine()) != null) {
                for (char c : line.toCharArray()) {
                    if (c == '"') {
                        insideQuotes = !insideQuotes;
                    } else if (!insideQuotes && c == ',') {
                        afterComma = true;
                    }
                }

                writer.write(line);
                writer.newLine();

                if (afterComma) {
                    writer.newLine(); // Add an empty line after each JSON argument
                    afterComma = false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Rename the temporary file to the original file
        // Note: This operation overwrites the original file with the modified content
        try {
            Files.move(Paths.get(filePath + "_temp"), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
