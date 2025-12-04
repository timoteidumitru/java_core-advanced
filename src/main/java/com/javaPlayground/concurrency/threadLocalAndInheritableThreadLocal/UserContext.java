package com.javaPlayground.concurrency.threadLocalAndInheritableThreadLocal;

public class UserContext {
    private static final InheritableThreadLocal<String> currentUser =
            new InheritableThreadLocal<>();

    public static void setUser(String user) {
        currentUser.set(user);
    }

    public static String getUser() {
        return currentUser.get();
    }

    public static void clear() {
        currentUser.remove();
    }
}

