package com.example.mindmines.requests;

import android.util.Pair;

import com.example.mindmines.models.user.User;

import java.util.ArrayList;
import java.util.List;

public class UserRequestSender {
    public static String registerRequestSend(String email, String password) {
        // TODO: заменить на настоящую отправку данных серверу.
        // send("POST, "/user/?=email&password")
        if (email == null || email.isEmpty()) return null;
        return email + "_token";
    }

    public static String loginRequestSend(String email, String password) {
        // TODO: заменить на настоящую отправку данных серверу.
        // send("GET", "/user/?=email&password")
        if (email == null || email.isEmpty()) return null;
        return email + "_token";
    }

    public static List<User> getFriends(String userId) {
        // TODO: заменить на настоящую отправку данных серверу.
        List<User> arr = new ArrayList<>();
        arr.add(new User("friend 1", 3));
        arr.add(new User("friend 2", 2));
        arr.add(new User("friend 3", 1));
        return arr;
    }
}
