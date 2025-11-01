package com.javaPlayground.exceptions.runtimeException.unchecked;


// 1️⃣ Base unchecked exception
public class ApplicationRuntimeException extends RuntimeException {
    public ApplicationRuntimeException(String message) {
        super(message);
    }
}
