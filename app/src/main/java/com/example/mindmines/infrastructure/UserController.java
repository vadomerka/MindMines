package com.example.mindmines.infrastructure;

import android.content.Context;
import android.content.res.Resources;

import com.example.mindmines.models.exceptions.AlreadyExistsException;
import com.example.mindmines.models.user.UserDTO;
import com.example.mindmines.requests.AuthRequestSender;
import com.example.mindmines.requests.UserRequestSender;
import com.example.mindmines.services.managers.UserStatusManager;
import com.example.mindmines.views.user.FriendsView;
import com.example.mindmines.views.user.LoginView;
import com.example.mindmines.views.user.RegistrationView;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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

    public void register(RegistrationView root, String email, String password) {
        executor.submit(() -> {
            try {
                String token = AuthRequestSender.getInstance().registerRequestSend(email, password);
                root.handleToken(token);
            } catch (JSONException | IOException | Resources.NotFoundException ex) {
                root.handleException(ex);
            } catch (AlreadyExistsException ex) {
                root.handleAlreadyExists(ex.getMessage());
            }
        });
    }

    public void login(LoginView root, String email, String password) {
        executor.submit(() -> {
            try {
                String token = AuthRequestSender.getInstance().loginRequestSend(email, password);
                root.handleToken(token);
            } catch (JSONException | IOException | Resources.NotFoundException |
                     AlreadyExistsException ex) {
                root.handleException(ex);
            }
        });
    }

    public void deleteUser(String email, String password) {
        UserStatusManager.getInstance(context).tryRemoveStatus(email + "_token");
    }
}
