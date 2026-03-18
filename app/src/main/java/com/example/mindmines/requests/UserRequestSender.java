package com.example.mindmines.requests;

import android.util.Pair;

import com.example.mindmines.models.dto.UserDTO;

import java.util.ArrayList;
import java.util.List;

public class UserRequestSender {
    public static Pair<String, Integer> getAuth(String email, String password) {
        // TODO: заменить на настоящую отправку данных серверу.
        String tmpToken = email + password;
        Integer userId = 1;
        return new Pair<>(tmpToken, userId);
    }

    public static List<UserDTO> getFriends(String userId) {
        // TODO: заменить на настоящую отправку данных серверу.
        List<UserDTO> arr = new ArrayList<>();
        arr.add(new UserDTO(1, "friend 1", 3));
        arr.add(new UserDTO(2, "friend 2", 2));
        arr.add(new UserDTO(3, "friend 3", 1));
        return arr;
    }
}
