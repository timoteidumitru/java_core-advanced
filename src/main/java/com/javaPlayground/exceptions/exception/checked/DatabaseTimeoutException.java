package com.javaPlayground.exceptions.exception.checked;


// ---- 3️⃣ Third level: A more specific data exception ----
public class DatabaseTimeoutException extends DataAccessException {
    public DatabaseTimeoutException(String message) {
        super(message);
    }
}
