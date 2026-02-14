package com.example.mindmines.requests;

import android.util.Pair;

public class UserRequestSender {
    public static Pair<String, Integer> getAuth(String email, String password) {
        // TODO: заменить на настоящую отправку данных серверу.
        String tmpToken = email + password;
        Integer userId = 1;
        return new Pair<>(tmpToken, userId);
    }
}
