package com.example.mindmines.views.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.widget.Button;
import android.widget.EditText;

import com.example.mindmines.R;
import com.example.mindmines.controllers.UserController;
import com.example.mindmines.services.auth.AuthManager;
import com.example.mindmines.views.habit.HabitsView;

public class LoginView extends AppCompatActivity {
    protected AuthManager authManager;
    protected EditText emailInput;
    protected EditText passwordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_login);

        emailInput = findViewById(R.id.login_email_input);
        passwordInput = findViewById(R.id.login_password_input);
        authManager = new AuthManager(getApplicationContext());
        Button loginBtn = findViewById(R.id.login_button);

        loginBtn.setOnClickListener(v -> tryLogIn());
    }

    private void tryLogIn() {
        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();

        if (!email.isEmpty() && !password.isEmpty())
        {
            // TODO: add server check.
            Pair<String, Integer> res = UserController.getAuthData(email, password);
            authManager.saveUserData(res.first, res.second.toString());

            Intent myIntent = new Intent(LoginView.this, HabitsView.class);
            LoginView.this.startActivity(myIntent);
            finish();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (authManager.isUserLoggedIn()) {
            Intent myIntent = new Intent(LoginView.this, HabitsView.class);
            LoginView.this.startActivity(myIntent);
            finish();
        }
        System.out.println("User not logged in!");
    }
}