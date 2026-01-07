package com.example.mindmines.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mindmines.R;
import com.example.mindmines.services.auth.AuthManager;

public class ProfileView extends AppCompatActivity {
    private AuthManager auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.user_profile);
        auth = new AuthManager(getApplicationContext());

        Button btn = findViewById(R.id.logout_btn);
        btn.setOnClickListener(v -> logout());

        updateText();
    }

    private void logout() {
        auth.logout();
        Intent myIntent = new Intent(ProfileView.this, LoginView.class);
        ProfileView.this.startActivity(myIntent);
        finish();
    }

    private void updateText() {
        // TODO: change to auth token and id;
        String email = auth.getAuthToken();
        String password = auth.getUserId();

        TextView tv = findViewById(R.id.default_text);
        tv.setText(email);
    }
}
