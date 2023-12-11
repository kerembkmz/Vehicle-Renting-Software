package com.example.vehiclerenting.Models;

import org.springframework.stereotype.Component;

@Component
public class UserContextHolder {
    private static final ThreadLocal<User> userContext = new ThreadLocal<>();

    public static void setUser(User user) {
        userContext.set(user);
    }

    public static User getUser() {
        return userContext.get();
    }

    public static void clear() {
        userContext.remove();
    }
}
