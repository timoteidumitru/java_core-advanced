package com.javaPlayground.exceptions.runtimeException.unchecked;


// 3️⃣ Deepest level unchecked exception
public class EmailValidationException extends ValidationException {
    public EmailValidationException(String message) {
        super(message);
    }
}
