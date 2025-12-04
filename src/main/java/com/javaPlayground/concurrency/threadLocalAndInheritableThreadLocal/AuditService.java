package com.javaPlayground.concurrency.threadLocalAndInheritableThreadLocal;

public class AuditService {

    // Simulate slow async audit logging
    public static void auditAction(String msg) {
        new Thread(() -> {
            System.out.printf("[Child Thread] user=%s | audit=%s%n",
                    UserContext.getUser(), msg);
        }).start();
    }
}

