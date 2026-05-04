package com.example.mindmines.views.user;

import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mindmines.MainActivity;
import com.example.mindmines.R;
import com.example.mindmines.infrastructure.UserController;
import com.example.mindmines.services.auth.AuthManager;

public class RegistrationView extends AppCompatActivity {
    protected AuthManager authManager;
    protected EditText emailInput;
    protected EditText passwordInput;
    protected EditText passwordConfirmInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_register);

        emailInput = findViewById(R.id.login_email_input);
        passwordInput = findViewById(R.id.login_password_input);
        passwordConfirmInput = findViewById(R.id.login_password_confirmation_input);
        authManager = new AuthManager(getApplicationContext());
        Button loginBtn = findViewById(R.id.register_page_button);

        loginBtn.setOnClickListener(v -> register());
    }

    private void register() {
        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();

        if (!email.isEmpty() && !password.isEmpty()) {
            if (!checkData()) { return; }

            Pair<String, Integer> res = UserController.getAuthData(email, password);
            authManager.saveUserData(res.first, res.second.toString());

            Intent myIntent = new Intent(RegistrationView.this, MainActivity.class);
            RegistrationView.this.startActivity(myIntent);
            finish();
        }
    }

    private boolean checkData() {
        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();
        String password2 = passwordConfirmInput.getText().toString();
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
    protected void onStart() {
        super.onStart();
        if (authManager.isUserLoggedIn()) {
            Intent myIntent = new Intent(RegistrationView.this, MainActivity.class);
            RegistrationView.this.startActivity(myIntent);
            finish();
        }
        System.out.println("User not logged in!");
    }
}