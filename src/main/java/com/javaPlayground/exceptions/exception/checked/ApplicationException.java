package com.javaPlayground.exceptions.exception.checked;

// ---- 1️⃣ Base custom exception (extends Exception) ----
public class ApplicationException extends Exception {
    public ApplicationException(String message) {
        super(message);
    }
}
