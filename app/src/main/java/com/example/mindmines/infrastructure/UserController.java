package com.example.mindmines.infrastructure;

import android.util.Pair;

import com.example.mindmines.models.dto.UserDTO;
import com.example.mindmines.requests.UserRequestSender;

import java.util.List;

public class UserController {
    public static Pair<String, Integer> getAuthData(String email, String password) {
        return UserRequestSender.getAuth(email, password);
    }

    public static List<UserDTO> getFriends(String userId) {
        return UserRequestSender.getFriends(userId);
    }
}
