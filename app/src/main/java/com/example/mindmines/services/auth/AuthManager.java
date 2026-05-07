package com.example.mindmines.services.auth;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.mindmines.models.user.UserStatus;
import com.example.mindmines.services.factories.UserStatusFactory;
import com.example.mindmines.services.managers.UserStatusManager;

public class AuthManager {

    private static final String SHARED_PREFS_NAME = "auth_prefs";
    private static final String KEY_AUTH_TOKEN = "auth_token";
    private static final String KEY_USER_EMAIL = "auth_email";

    private final SharedPreferences sharedPreferences;
    private final Context context;

    public AuthManager(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        this.context = context;
    }

    public void saveUserData(String authToken, String email) {
        sharedPreferences.edit()
                .putString(KEY_AUTH_TOKEN, authToken)
                .putString(KEY_USER_EMAIL, email)
                .apply();
    }

    public void saveNewUserData(String authToken, String email) {
        sharedPreferences.edit()
                .putString(KEY_AUTH_TOKEN, authToken)
                .putString(KEY_USER_EMAIL, email)
                .apply();
        new UserStatusManager(context).addStatus(UserStatusFactory.getInstance().create(authToken));
    }

    public String getAuthToken() {
        return sharedPreferences.getString(KEY_AUTH_TOKEN, null);
    }

    public String getUserId() {
        // В этой версии userId = userToken.
        String token = sharedPreferences.getString(KEY_AUTH_TOKEN, null);
        Log.d("Debug Auth", String.format("getUserId: %s", token));
        return token;
    }

    public String getEmail() {
        return getUserId(); // return new UserStatusManager(context).getStatus().getEmail();
    }

    public boolean isUserLoggedIn() {
        UserStatus status = new UserStatusManager(context).getStatus();
        return status != null;
    }

    public void logout() {
//        DataSynchronizerManager.getInstance(context).saveToDB();

        sharedPreferences.edit()
                .remove(KEY_AUTH_TOKEN)
                .remove(KEY_USER_EMAIL)
                .apply();
    }
}