package com.example.authtoken.context;

public class UserContext {
    private static ThreadLocal<String> userThreadLocal = new ThreadLocal<>();

    public static void setUser(String user) {
        userThreadLocal.set(user);
    }

    public static String getUser() {
        return userThreadLocal.get();
    }

    public static void clearUser() {
        userThreadLocal.remove();
    }
}
