package com.javaPlayground.concurrency.threadLocalAndInheritableThreadLocal;

public class UserActionHandler {

    public void performAction(String user, String action) {
        UserContext.setUser(user);

        System.out.printf("[Main Thread] user=%s | action=%s%n",
                UserContext.getUser(), action);

        AuditService.auditAction("User performed: " + action);

        UserContext.clear();
    }

    public static void main(String[] args) throws Exception {

        UserActionHandler handler = new UserActionHandler();

        handler.performAction("alice", "DELETE_ACCOUNT");
        handler.performAction("bob", "UPLOAD_FILE");

        Thread.sleep(500); // wait for child threads
    }
}

