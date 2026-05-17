package com.example.mindmines.views.user;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mindmines.MainActivity;
import com.example.mindmines.R;

public class RegistrationView extends LoginView {
    protected EditText passwordConfirmInput;
    protected String password2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected int getLayout() {
        return R.layout.user_register;
    }

    @Override
    protected void initUi() {
        initForm();
        initButtons();
    }

    @Override
    protected void initForm() {
        super.initForm();
        passwordConfirmInput = findViewById(R.id.login_password_confirmation_input);
    }

    @Override
    protected void initButtons() {
        Button loginBtn = findViewById(R.id.register_button);
        loginBtn.setOnClickListener(v -> register());
    }

    protected void register() {
        email = emailInput.getText().toString();
        password = passwordInput.getText().toString();
        loadingIndicator.setVisibility(View.VISIBLE);
        exceptionView.setVisibility(View.GONE);

        if (!checkData()) {
            return;
        }

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
        Log.d(TAG, "handleToken: ");
        runOnUiThread(() -> {
            loadingIndicator.setVisibility(View.GONE);
        });
        authManager.saveNewUserData(token, email);
        openMain();
    }

    public void handleAlreadyExists(String token) {
        showException("Пользователь уже зарегестрирован.");
        authManager.saveUserData(token, email);
        openMain();
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