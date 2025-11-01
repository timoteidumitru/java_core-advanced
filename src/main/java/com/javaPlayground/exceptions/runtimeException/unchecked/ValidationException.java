package com.javaPlayground.exceptions.runtimeException.unchecked;


// 2️⃣ Mid-level unchecked exceptions
public class ValidationException extends ApplicationRuntimeException {
    public ValidationException(String message) {
        super(message);
    }
}
