package com.example.mindmines.requests;

import android.util.Pair;

import com.example.mindmines.MainActivity;
import com.example.mindmines.models.user.User;
import com.example.mindmines.models.user.UserStatus;
import com.example.mindmines.services.auth.AuthManager;
import com.example.mindmines.services.repositories.RepositoryService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserRequestSender {
    private static String generateLocalToken(String email, String password) {
        return email + "_token";
    }

    private static boolean userExists(String token) {
        List<String> allData = RepositoryService.getUserStatusRepository().getAll().stream()
                .map(UserStatus::getUserId).collect(Collectors.toList());
        return allData.contains(token);
    }

    public static String registerRequestSend(String email, String password) {
        // TODO: заменить на настоящую отправку данных серверу.
        // send("POST, "/user/?=email&password")
        if (email == null || email.isEmpty()) return null;

        String token = generateLocalToken(email, password);
        if (userExists(token)) return null;
        return token;
    }

    public static String loginRequestSend(String email, String password) {
        // TODO: заменить на настоящую отправку данных серверу.
        // send("GET", "/user/?=email&password")
        if (email == null || email.isEmpty()) return null;

        String token = generateLocalToken(email, password);
        if (userExists(token)) {
            return token;
        }
        return null;
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
