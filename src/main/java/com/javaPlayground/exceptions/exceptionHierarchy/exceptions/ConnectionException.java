package com.javaPlayground.exceptions.exceptionHierarchy.exceptions;

// Level 3: Subclass of DatabaseException
public class ConnectionException extends DatabaseException {
    public ConnectionException(String message) {
        super(message);
    }
}
