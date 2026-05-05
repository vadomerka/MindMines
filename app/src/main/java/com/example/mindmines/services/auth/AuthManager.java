package com.example.mindmines.services.auth;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.mindmines.models.user.UserStatus;
import com.example.mindmines.services.factories.UserStatusFactory;
import com.example.mindmines.services.managers.UserStatusManager;

public class AuthManager {

    private static final String SHARED_PREFS_NAME = "auth_prefs";

    private final SharedPreferences sharedPreferences;
    private final Context context;

    public AuthManager(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        this.context = context;
    }

    public void saveUserData(String authToken, String email) {
        new UserStatusManager(context).setStatus(UserStatusFactory.getInstance().create(authToken));
    }

    public String getAuthToken() {
        return new UserStatusManager(context).getStatus().getId();
    }

    public String getUserId() {
        // В этой версии userId = userToken.
        return new UserStatusManager(context).getStatus().getId();
    }

    public String getEmail() {
        return getUserId(); // return new UserStatusManager(context).getStatus().getEmail();
    }

    public boolean isUserLoggedIn() {
        UserStatus status = new UserStatusManager(context).getStatus();
        return status != null;
    }

    public void logout() {
        new UserStatusManager(context).removeStatus();
    }
}