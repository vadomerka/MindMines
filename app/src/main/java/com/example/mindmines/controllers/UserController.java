package com.example.mindmines.controllers;

import android.util.Pair;

import com.example.mindmines.requests.UserRequestSender;

public class UserController {
    public static Pair<String, Integer> getAuthData(String email, String password) {
        return UserRequestSender.getAuth(email, password);
    }
}
