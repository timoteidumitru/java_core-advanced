package com.javaPlayground.exceptions.exception.checked;


// ---- 2️⃣ Second level: Business exceptions ----
public class BusinessException extends ApplicationException {
    public BusinessException(String message) {
        super(message);
    }
}
