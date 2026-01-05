package com.example.mindmines;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.mindmines.models.Habit;
import com.example.mindmines.models.User;
import com.google.gson.Gson;

import java.time.OffsetDateTime;
import java.time.chrono.ChronoLocalDate;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
}