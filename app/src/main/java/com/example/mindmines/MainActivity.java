package com.example.mindmines;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
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

        // TODO: добавить уведомления https://www.geeksforgeeks.org/android/schedule-notifications-in-android/
        createNotificationChannel();
    }

    protected void createNotificationChannel() {
        String channelId = "habit_channel";
        String channelName = "Уведомления о привычках";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
        channel.setDescription("Канал для уведомления о скором выполнении привычек.");

        NotificationManager manager = getSystemService(NotificationManager.class);
        if (manager != null) {
            manager.createNotificationChannel(channel);
        }
    }
}

