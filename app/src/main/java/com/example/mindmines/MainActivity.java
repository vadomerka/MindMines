package com.example.mindmines;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.mindmines.services.auth.AuthManager;
import com.example.mindmines.views.HabitView;
import com.example.mindmines.views.LoginView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent myIntent = new Intent(MainActivity.this, HabitView.class);
        if (!new AuthManager(getApplicationContext()).isUserLoggedIn()) {
            myIntent = new Intent(MainActivity.this, LoginView.class);
        }
        MainActivity.this.startActivity(myIntent);
        finish();
    }
}

