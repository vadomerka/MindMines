package com.example.mindmines.views.user;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.mindmines.R;
import com.example.mindmines.models.UserStatus;
import com.example.mindmines.services.managers.UserStatusManager;
import com.example.mindmines.services.auth.AuthManager;
import com.example.mindmines.views.BaseActivity;

public class ProfileView extends BaseActivity {
    private AuthManager auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth = new AuthManager(getApplicationContext());

        TextView tv = findViewById(R.id.navigation_title_view);
        tv.setText("Ваш профиль:");
        Button btn = findViewById(R.id.logout_btn);
        btn.setOnClickListener(v -> logout());
        Button navButton = findViewById(R.id.bottom_navigation_bar1);
        navButton.setEnabled(false);

        updateUserStatus();
    }

    @Override
    protected int getContentLayoutId() { return R.layout.user_profile; }

    @Override
    protected Context getCurrentContext() { return ProfileView.this; }

    private void logout() {
        auth.logout();
        Intent myIntent = new Intent(ProfileView.this, LoginView.class);
        ProfileView.this.startActivity(myIntent);
        finish();
    }

    @SuppressLint("SetTextI18n")
    public void updateUserStatus() {
        // TODO: change to auth token and id;
        String email = auth.getAuthToken();
        TextView profileTitle = findViewById(R.id.user_info_view);
        profileTitle.setText(email);

        UserStatus status = UserStatusManager.getStatus();
        TextView levelValue = findViewById(R.id.user_level_value_view);
        TextView expValue = findViewById(R.id.user_exp_value_view);
        TextView expUntilValue = findViewById(R.id.user_exp_until_level_value_view);
        levelValue.setText(status.getLevel().toString());
        expValue.setText(status.getExperience().toString());
        expUntilValue.setText(String.valueOf(status.getMaxExperience() - status.getExperience()));
    }
}
