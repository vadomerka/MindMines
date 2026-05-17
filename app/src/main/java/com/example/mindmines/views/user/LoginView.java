package com.example.mindmines.views.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mindmines.MainActivity;
import com.example.mindmines.R;
import com.example.mindmines.infrastructure.UserController;
import com.example.mindmines.services.auth.AuthManager;
import com.google.android.material.button.MaterialButton;

import org.json.JSONException;

import java.io.IOException;

public class LoginView extends AppCompatActivity {
    protected AuthManager authManager;
    protected EditText emailInput;
    protected EditText passwordInput;
    protected ProgressBar loadingIndicator;
    protected TextView exceptionView;
    protected UserController uc;

    protected String email;
    protected String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_login);

        uc = UserController.getInstance(getApplicationContext());
        authManager = new AuthManager(getApplicationContext());

        initUi();
    }

    protected void initUi() {
        emailInput = findViewById(R.id.login_email_input);
        passwordInput = findViewById(R.id.login_password_input);
        loadingIndicator = findViewById(R.id.loading_indicator);
        exceptionView = findViewById(R.id.exception_text_view);
        Button loginBtn = findViewById(R.id.login_button);
        loginBtn.setOnClickListener(v -> tryLogIn());
        Button registerBtn = findViewById(R.id.register_page_button);
        registerBtn.setOnClickListener(v -> openRegisterView());


        initDebugUi();
    }

    protected void initDebugUi() {
        if (!MainActivity.isDebug()) return;
        LinearLayout changeUrlLayout = findViewById(R.id.change_server_url_button_layout);
        changeUrlLayout.setVisibility(View.VISIBLE);
        MaterialButton changeUrlBtn = findViewById(R.id.change_server_url_button);
        changeUrlBtn.setOnClickListener(v -> openServerUrlChangeView());
    }

    protected void tryLogIn() {
        email = emailInput.getText().toString();
        password = passwordInput.getText().toString();
        exceptionView.setVisibility(View.GONE);

        if (!checkData()) {
            return;
        }

        uc.login(this, email, password);
    }

    protected boolean checkData() {
        email = emailInput.getText().toString();
        password = passwordInput.getText().toString();
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Форма не заполнена", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void handleToken(String token) {
        runOnUiThread(() -> { loadingIndicator.setVisibility(View.GONE); });
        if (token == null) {
            showException("Пользователь не найден.");
        } else {
            authManager.saveUserData(token, email);
            openMain();
        }
    }

    public void handleException(Exception ex) {
        if (ex instanceof IOException) {
            showException("Произошла ошибка подключения");
        } else if (ex instanceof JSONException) {
            showException("Произошла ошибка при десериализации запроса");
        }
    }

    protected void showException(String message) {
        runOnUiThread(() -> {
            loadingIndicator.setVisibility(View.GONE);
            exceptionView.setVisibility(View.VISIBLE);
            exceptionView.setText(message);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (authManager.isUserLoggedIn()) {
            openMain();
        }
        System.out.println("User not logged in!");
    }

    protected void openMain() {
        Intent myIntent = new Intent(LoginView.this, MainActivity.class);
        LoginView.this.startActivity(myIntent);
        finish();
    }

    protected void openRegisterView() {
        Intent myIntent = new Intent(LoginView.this, RegistrationView.class);
        LoginView.this.startActivity(myIntent);
        finish();
    }

    protected void openServerUrlChangeView() {
        Intent myIntent = new Intent(LoginView.this, ServerUrlChangeView.class);
        LoginView.this.startActivity(myIntent);
        finish();
    }
}