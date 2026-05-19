package com.example.mindmines.views.user;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

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

    protected String TAG = "Debug register";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());

        uc = UserController.getInstance(getApplicationContext());
        authManager = new AuthManager(getApplicationContext());

        initUi();
    }

    protected int getLayout() {
        return R.layout.user_login;
    }

    protected void initUi() {
        initForm();
        initButtons();
        initDebugUi();
    }

    protected void initForm() {
        emailInput = findViewById(R.id.login_email_input);
        passwordInput = findViewById(R.id.login_password_input);
        loadingIndicator = findViewById(R.id.loading_indicator);
        exceptionView = findViewById(R.id.exception_text_view);
    }

    protected void initButtons() {
        Button loginBtn = findViewById(R.id.login_button);
        loginBtn.setOnClickListener(v -> tryLogIn());
        Button registerBtn = findViewById(R.id.register_page_button);
        registerBtn.setOnClickListener(v -> openRegisterView());
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
        if (!checkData()) {
            return;
        }

        loadingIndicator.setVisibility(View.VISIBLE);
        exceptionView.setVisibility(View.GONE);

        uc.login(this, email, password);
    }

    protected boolean checkData() {
        email = emailInput.getText().toString();
        password = passwordInput.getText().toString();
        if (email.isEmpty() || password.isEmpty()) {
            showException("Форма не заполнена");
            return false;
        }
        return true;
    }

    public void handleToken(String token) {
        runOnUiThread(() -> {
            loadingIndicator.setVisibility(View.GONE);
        });
        if (token == null) {
            showException("Пользователь с таким логином и паролем не существует.");
        } else {
            authManager.saveUserData(token, email);
            openMain();
        }
    }

    public void handleException(Exception ex) {
        Log.d(TAG, "handleException: ");
        if (ex instanceof IOException) {
            showException("Не удалось подключиться");
        } else if (ex instanceof JSONException) {
            showException("Произошла ошибка при десериализации запроса");
        } else if (ex instanceof Resources.NotFoundException) {
            showException("Пользователь с таким логином и паролем не существует.");
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