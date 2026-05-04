package com.example.mindmines.requests;

import android.util.Pair;

import com.example.mindmines.models.user.User;

import java.util.ArrayList;
import java.util.List;

public class UserRequestSender {
    public static Pair<String, Integer> getAuth(String email, String password) {
        // TODO: заменить на настоящую отправку данных серверу.
        String tmpToken = email + "_token";
        Integer userId = 1;
        return new Pair<>(tmpToken, userId);
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
