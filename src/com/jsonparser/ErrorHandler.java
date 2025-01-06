package com.jsonparser;

public class ErrorHandler {

    public static void handleError(String message) {
        System.err.println("Error: " + message);
        System.exit(1);
    }
}
