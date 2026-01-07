package com.example.mindmines.services.auth;

import android.content.Context;
import android.content.SharedPreferences;

public class AuthManager {

    private static final String SHARED_PREFS_NAME = "auth_prefs";
    private static final String KEY_AUTH_TOKEN = "auth_token";
    private static final String KEY_USER_ID = "user_id";

    private final SharedPreferences sharedPreferences;

    public AuthManager(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void saveUserData(String authToken, String userId) {
        sharedPreferences.edit()
                .putString(KEY_AUTH_TOKEN, authToken)
                .putString(KEY_USER_ID, userId)
                .apply();
    }

    public String getAuthToken() {
        return sharedPreferences.getString(KEY_AUTH_TOKEN, null);
    }

    public String getUserId() {
        return sharedPreferences.getString(KEY_USER_ID, null);
    }

    public boolean isUserLoggedIn() {
        return sharedPreferences.contains(KEY_AUTH_TOKEN) &&
                sharedPreferences.contains(KEY_USER_ID);
    }

    public void logout() {
        sharedPreferences.edit()
                .remove(KEY_AUTH_TOKEN)
                .remove(KEY_USER_ID)
                .apply();
    }
}