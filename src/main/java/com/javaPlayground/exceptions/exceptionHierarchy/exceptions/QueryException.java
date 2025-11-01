package com.javaPlayground.exceptions.exceptionHierarchy.exceptions;

// Level 4: Another subclass of DatabaseException
class QueryException extends DatabaseException {
    public QueryException(String message) {
        super(message);
    }
}
