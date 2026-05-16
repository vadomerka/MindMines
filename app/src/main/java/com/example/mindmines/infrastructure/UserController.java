package com.example.mindmines.infrastructure;

import android.content.Context;

import com.example.mindmines.models.user.UserDTO;
import com.example.mindmines.requests.AuthRequestSender;
import com.example.mindmines.requests.UserRequestSender;
import com.example.mindmines.services.managers.UserStatusManager;
import com.example.mindmines.views.user.FriendsView;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicReference;

public class UserController {
    private static UserController instance;
    private final AtomicReference<String> token;
    private final ExecutorService executor;
    private final Context context;
    private List<UserDTO> users;

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

    public void getFriends(FriendsView root, String userId) {
        executor.submit(() -> {
            try {
                users = UserRequestSender.getInstance().getFriendsRequestSend(userId);
                root.updateFriends(users);
            } catch (JSONException | IOException ex) {
                root.handleException(ex);
            }
        });
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
