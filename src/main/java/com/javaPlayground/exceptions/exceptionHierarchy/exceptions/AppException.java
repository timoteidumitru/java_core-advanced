package com.javaPlayground.exceptions.exceptionHierarchy.exceptions;

// Level 1: Base exception
public class AppException extends Exception {
    public AppException(String message) {
        super(message);
    }
}
