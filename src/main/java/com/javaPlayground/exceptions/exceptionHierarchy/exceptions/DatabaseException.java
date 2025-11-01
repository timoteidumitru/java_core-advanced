package com.javaPlayground.exceptions.exceptionHierarchy.exceptions;

// Level 2: Subclass of AppException
public class DatabaseException extends AppException {
    public DatabaseException(String message) {
        super(message);
    }
}
