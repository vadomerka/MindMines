package com.example.mindmines.infrastructure;

import android.util.Pair;

import com.example.mindmines.models.user.User;
import com.example.mindmines.requests.UserRequestSender;

import java.util.List;

public class UserController {
    public static String register(String email, String password) {
        return UserRequestSender.registerRequestSend(email, password);
    }

    public static String login(String email, String password) {
        return UserRequestSender.loginRequestSend(email, password);
    }

    public static List<User> getFriends(String userId) {
        return UserRequestSender.getFriends(userId);
    }
}
