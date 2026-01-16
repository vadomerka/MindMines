package com.example.mindmines;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.mindmines.services.auth.AuthManager;
import com.example.mindmines.services.repositories.HabitRepository;
import com.example.mindmines.views.habit.HabitsView;
import com.example.mindmines.views.user.LoginView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO: change to loading from server.
        HabitRepository.init();

        Intent myIntent = new Intent(MainActivity.this, HabitsView.class);
        if (!new AuthManager(getApplicationContext()).isUserLoggedIn()) {
            myIntent = new Intent(MainActivity.this, LoginView.class);
        }
        MainActivity.this.startActivity(myIntent);
        finish();
    }
}

