package com.jsonparser;

import java.nio.file.*;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String jsonString = loadJsonFile("valid.json");

        if (jsonString == null) {
            System.out.println("Error: JSON file could not be loaded.");
            return;
        }

        Tokenizer tokenizer = new Tokenizer();
        List<Tokenizer.Token> tokens = tokenizer.tokenize(jsonString);

        System.out.println("Tokens:");
        for (Tokenizer.Token token : tokens) {
            System.out.println(token.getType() + ": " + token.getValue());
        }

        Parser parser = new Parser(tokens);
        Object parsedJson = parser.parse();

        System.out.println("\nParsed JSON:");
        System.out.println(parsedJson);
    }

    private static String loadJsonFile(String filename) {
        try {
            ClassLoader classLoader = Main.class.getClassLoader();
            java.net.URL resource = classLoader.getResource(filename);
            if (resource == null) {
                System.out.println("File not found: " + filename);
                return null;
            }
            Path path = Paths.get(resource.toURI());
            return Files.readString(path);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
