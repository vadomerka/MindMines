package com.example.mindmines;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.mindmines.models.user.UserStatus;
import com.example.mindmines.services.auth.AuthManager;
import com.example.mindmines.services.managers.CharManager;
import com.example.mindmines.services.managers.UserStatusManager;
import com.example.mindmines.services.repositories.RepositoryService;
import com.example.mindmines.services.repositories.dao.UserStatusRepository;
import com.example.mindmines.services.timers.DataBackupTimer;
import com.example.mindmines.services.timers.HabitStatusCheckerTimer;
import com.example.mindmines.views.user.LoginView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Debug MainActivity";
    private static final boolean DEBUG = true;

    public static boolean isDebug() {
        return DEBUG;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initNavigation();

        RepositoryService.initAll(getApplicationContext());

        RepositoryService.getUserStatusRepository().subscribe(upd -> {
            UserStatusRepository usr = RepositoryService.getUserStatusRepository();
            for (UserStatus us : usr.getAll()) {
                Log.d("Debug UserStatus rep", us.getId() + " " + us.getExperience() + " " + us.getMaxExperience() + " " + us.getLevel());
            }
        });

        if (!new AuthManager(getApplicationContext()).isUserLoggedIn()) {
            Intent myIntent = new Intent(MainActivity.this, LoginView.class);
            MainActivity.this.startActivity(myIntent);
            finish();
            return;
        }


        CharManager.getInstance(getApplicationContext()).unlockAvailableChars(
                UserStatusManager.getInstance(getApplicationContext()).getStatus());

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
    }

    private void initNavigation() {
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation_bar);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(bottomNav, navController);

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            int topLevelDestinationId = resolveTopLevelDestination(destination);
            if (topLevelDestinationId == 0) {
                return;
            }
            // Обновляем только визуальную подсветку таба, не инициируя новую навигацию.
            bottomNav.getMenu().findItem(topLevelDestinationId).setChecked(true);
        });
    }

    private int resolveTopLevelDestination(NavDestination destination) {
        int id = destination.getId();
        if (id == R.id.charFragment) {
            return R.id.partyFragment;
        }
        if (id == R.id.habitAddFragment || id == R.id.habitChangeFragment) {
            return R.id.habitsFragment;
        }
        if (id == R.id.habitsFragment
                || id == R.id.partyFragment
                || id == R.id.friendsFragment
                || id == R.id.assistantFragment
                || id == R.id.profileFragment) {
            return id;
        }
        return 0;
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy");

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
}

