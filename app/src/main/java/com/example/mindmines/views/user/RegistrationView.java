package com.example.mindmines.views.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mindmines.MainActivity;
import com.example.mindmines.R;

import org.json.JSONException;

import java.io.IOException;

public class RegistrationView extends LoginView {
    protected EditText passwordConfirmInput;
    protected String password2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_register);
    }

    @Override
    protected void initUi() {
        emailInput = findViewById(R.id.login_email_input);
        passwordInput = findViewById(R.id.login_password_input);
        passwordConfirmInput = findViewById(R.id.login_password_confirmation_input);
        Button loginBtn = findViewById(R.id.register_page_button);
        loginBtn.setOnClickListener(v -> register());
    }

    private void register() {
        email = emailInput.getText().toString();
        password = passwordInput.getText().toString();

        if (!checkData()) { return; }

        uc.register(this, email, password);
    }

    @Override
    protected boolean checkData() {
        email = emailInput.getText().toString();
        password = passwordInput.getText().toString();
        password2 = passwordConfirmInput.getText().toString();
        if (email.isEmpty() || password.isEmpty() || password2.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Форма не заполнена", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!password.equals(password2)) {
            Toast.makeText(getApplicationContext(), "Пароли не совпадают", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public void handleToken(String token) {
        runOnUiThread(() -> { loadingIndicator.setVisibility(View.GONE); });
        if (token == null) {
            showException("Пользователь уже зарегестрирован.");
            authManager.saveUserData(token, email);
        } else {
            authManager.saveNewUserData(token, email);
            openMain();
        }
    }

    @Override
    public void handleException(Exception ex) {
        if (ex instanceof IOException) {
            showException("Произошла ошибка подключения");
        } else if (ex instanceof JSONException) {
            showException("Произошла ошибка при десериализации запроса");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (authManager.isUserLoggedIn()) {
            openMain();
        }
    }

    @Override
    protected void openMain() {
        Intent myIntent = new Intent(RegistrationView.this, MainActivity.class);
        RegistrationView.this.startActivity(myIntent);
        finish();
    }
}