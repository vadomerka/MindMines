package com.example.mindmines.views.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mindmines.MainActivity;
import com.example.mindmines.R;
import com.example.mindmines.infrastructure.UserController;
import com.example.mindmines.services.auth.AuthManager;
import com.google.android.material.button.MaterialButton;

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
        Button registerBtn = findViewById(R.id.register_page_button);

        LinearLayout changeUrlLayout = findViewById(R.id.change_server_url_button_layout);
        if (MainActivity.isDebug()) changeUrlLayout.setVisibility(View.VISIBLE);
        MaterialButton changeUrlBtn = findViewById(R.id.change_server_url_button);

        loginBtn.setOnClickListener(v -> tryLogIn());
        registerBtn.setOnClickListener(v -> openRegisterView());
        changeUrlBtn.setOnClickListener(v -> openServerUrlChangeView());
    }

    private void tryLogIn() {
        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();

        if (!checkData()) {
            return;
        }

        UserController uc = UserController.getInstance(getApplicationContext());
        String token = uc.login(email, password);
        if (token == null) {
            Toast.makeText(getApplicationContext(), "Пользователь не найден.", Toast.LENGTH_SHORT).show();
//            uc.deleteUser(email, password);
            return;
        }
        authManager.saveUserData(token, email);

        Intent myIntent = new Intent(LoginView.this, MainActivity.class);
        LoginView.this.startActivity(myIntent);
        finish();
    }

    private boolean checkData() {
        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Форма не заполнена", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void openRegisterView() {
        Intent myIntent = new Intent(LoginView.this, RegistrationView.class);
        LoginView.this.startActivity(myIntent);
        finish();
    }

    private void openServerUrlChangeView() {
        Intent myIntent = new Intent(LoginView.this, ServerUrlChangeView.class);
        LoginView.this.startActivity(myIntent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (authManager.isUserLoggedIn()) {
            Intent myIntent = new Intent(LoginView.this, MainActivity.class);
            LoginView.this.startActivity(myIntent);
            finish();
        }
        System.out.println("User not logged in!");
    }
}