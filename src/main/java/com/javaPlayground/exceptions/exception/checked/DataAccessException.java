package com.javaPlayground.exceptions.exception.checked;


// ---- 2️⃣ Second level: Data exceptions ----
class DataAccessException extends ApplicationException {
    public DataAccessException(String message) {
        super(message);
    }
}
