package com.example.mindmines.infrastructure;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.mindmines.models.chat.ChatMessage;
import com.example.mindmines.models.user.User;
import com.example.mindmines.requests.ChatMessagesRequestSender;
import com.example.mindmines.requests.UserRequestSender;
import com.example.mindmines.services.auth.AuthManager;
import com.google.common.util.concurrent.Futures;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicReference;

public class UserController {
    private static UserController instance;
    private final AtomicReference<String> token;
    private final ExecutorService executor;
    private final Handler mainHandler;

    public UserController() {
        this.token = new AtomicReference<>();
        this.executor = Executors.newSingleThreadExecutor();
        this.mainHandler = new Handler(Looper.getMainLooper());
    }

    public static UserController getInstance() {
        if (instance == null) {
            instance = new UserController();
        }
        return instance;
    }

    public String register(String email, String password) {
        Future<?> future = executor.submit(() -> {

            token.set(UserRequestSender.registerRequestSend(email, password));

            mainHandler.post(() -> {});
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

            token.set(UserRequestSender.loginRequestSend(email, password));

            mainHandler.post(() -> {});
        });
        try {
            future.get();
            return token.get();
        } catch (ExecutionException | InterruptedException e) {
            return null;
        }
    }

    public static List<User> getFriends(String userId) {
        return UserRequestSender.getFriends(userId);
    }
}
