package com.example.mindmines;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import com.example.mindmines.db.datasync.DataSynchronizerManager;
import com.example.mindmines.services.auth.AuthManager;
import com.example.mindmines.services.repositories.RepositoryService;
import com.example.mindmines.services.timers.DataBackupTimer;
import com.example.mindmines.services.timers.HabitStatusCheckerTimer;
import com.example.mindmines.views.user.LoginView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Debug MainActivity";
    private static final boolean DEBUG = false;
    private static DataSynchronizerManager dbSync;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initNavigation();

        RepositoryService.initAll(this);
        dbSync = DataSynchronizerManager.getInstance(this);
        dbSync.loadFromDB();

        // Notifications init
        createNotificationChannel();

        // Timers init
        HabitStatusCheckerTimer.ensureScheduled(getApplicationContext());
        DataBackupTimer.ensureScheduled(getApplicationContext());

        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && !am.canScheduleExactAlarms()) {
            Log.w("MainActivity", "Точное время НЕ разрешено!");
            Intent intent = new Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM);
            intent.setData(Uri.parse("package:" + getPackageName()));
            startActivity(intent);
        }

        if (!new AuthManager(getApplicationContext()).isUserLoggedIn()) {
            Intent myIntent = new Intent(MainActivity.this, LoginView.class);
            MainActivity.this.startActivity(myIntent);
            finish();
        }
    }

//    private void initNavigation() {
//        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation_bar);
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        NavigationUI.setupWithNavController(bottomNav, navController);
//    }

    private void initNavigation() {
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation_bar);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(bottomNav, navController);
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy");
        dbSync.saveToDB();

        super.onDestroy();
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

    public static boolean isDebug() { return DEBUG; }
}

