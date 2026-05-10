package com.example.mindmines.infrastructure;

import android.content.Context;

import com.example.mindmines.models.user.UserDTO;
import com.example.mindmines.requests.AuthRequestSender;
import com.example.mindmines.requests.UserRequestSender;
import com.example.mindmines.services.managers.UserStatusManager;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicReference;

public class UserController {
    private static UserController instance;
    private final AtomicReference<String> token;
    private List<UserDTO> users;
    private final ExecutorService executor;
    private final Context context;

    public UserController(Context context) {
        this.token = new AtomicReference<>();
        this.executor = Executors.newSingleThreadExecutor();
        this.context = context;
    }

    public static UserController getInstance(Context context) {
        if (instance == null) {
            instance = new UserController(context);
        }
        return instance;
    }

    public List<UserDTO> getFriends(String userId) {
        Future<?> future = executor.submit(() -> {
            users = UserRequestSender.getInstance().getFriendsRequestSend(userId);
        });
        try {
            future.get();
            return users;
        } catch (ExecutionException | InterruptedException e) {
            return null;
        }
    }

    public String register(String email, String password) {
        Future<?> future = executor.submit(() -> {
            token.set(AuthRequestSender.getInstance().registerRequestSend(email, password));
        });
        try {
            future.get();
            return token.get();
        } catch (ExecutionException | InterruptedException e) {
            return null;
        }
    }

    public String login(String email, String password) {
        Future<?> future = executor.submit(() -> {
            token.set(AuthRequestSender.getInstance().loginRequestSend(email, password));
        });
        try {
            future.get();
            return token.get();
        } catch (ExecutionException | InterruptedException e) {
            return null;
        }
    }

    public void deleteUser(String email, String password) {
        UserStatusManager.getInstance(context).tryRemoveStatus(email + "_token");
    }
}
