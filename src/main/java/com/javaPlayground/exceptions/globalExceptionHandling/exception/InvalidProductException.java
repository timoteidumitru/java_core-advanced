package com.javaPlayground.exceptions.globalExceptionHandling.exception;

public class InvalidProductException extends RuntimeException {
    public InvalidProductException(String message) {
        super(message);
    }
}
