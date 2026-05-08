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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

public class UserController {
    private static UserController instance;
    private final Context context;
    private final AuthManager auth;
    private final List<String> tokens;
    private final Executor executor;
    private final Handler mainHandler;

    public UserController(Context context) {
        this.context = context;
        this.tokens = new ArrayList<>();
        this.auth = new AuthManager(context);
        this.executor = Executors.newSingleThreadExecutor();
        this.mainHandler = new Handler(Looper.getMainLooper());
    }

    public static UserController getInstance(Context context) {
        if (instance == null) {
            instance = new UserController(context);
        }
        return instance;
    }

    public void register(String email, String password) {
        executor.execute(() -> {

            tokens.add(UserRequestSender.registerRequestSend(email, password));

            mainHandler.post(() -> {
                String token = tokens.get(tokens.size() - 1);
                if (token == null) {
                    Toast.makeText(context, "Пользователь уже зарегестрирован.", Toast.LENGTH_SHORT).show();

                    login(email, password);

                    auth.saveUserData(tokens.get(tokens.size() - 1), email);
                } else {
                    auth.saveNewUserData(token, email);
                }
                tokens.clear();
            });
        });
    }

    public void login(String email, String password) {
        executor.execute(() -> {

            tokens.add(UserRequestSender.loginRequestSend(email, password));

            mainHandler.post(() -> {
                String token = tokens.get(tokens.size() - 1);

                if (token == null) {
                    Toast.makeText(context, "Пользователь не найден.", Toast.LENGTH_SHORT).show();
                    return;
                }
                auth.saveUserData(token, email);
            });
        });
    }

    public static List<User> getFriends(String userId) {
        return UserRequestSender.getFriends(userId);
    }
}
