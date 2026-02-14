package com.example.mindmines;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import com.example.mindmines.models.Habit;
import com.example.mindmines.models.dto.HabitDTO;
import com.example.mindmines.models.enums.HabitType;
import com.example.mindmines.services.MidnightCheckerReceiver;
import com.example.mindmines.services.auth.AuthManager;
import com.example.mindmines.services.factories.HabitFactory;
import com.example.mindmines.services.repositories.HabitRepository;
import com.example.mindmines.views.habit.HabitInterval;
import com.example.mindmines.views.habit.HabitTimeUnit;
import com.example.mindmines.views.habit.HabitsView;
import com.example.mindmines.views.user.LoginView;

import java.time.OffsetDateTime;

public class MainActivity extends AppCompatActivity {

    private static final boolean DEBUG = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (DEBUG) {
            test();
        } else {

            // TODO: change to loading from server.
            HabitRepository.init();

            // Notifications init
            createNotificationChannel();
            MidnightCheckerReceiver.ensureScheduled(getApplicationContext());

            AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && !am.canScheduleExactAlarms()) {
                Log.w("MainActivity", "Точное время НЕ разрешено! Открываем настройки...");
                Intent intent = new Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM);
                intent.setData(Uri.parse("package:" + getPackageName()));
                startActivity(intent);
            }

//            Intent intent = new Intent();
//            intent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
//            intent.setData(Uri.parse("package:" + getPackageName()));
//            startActivity(intent);

            Intent myIntent = new Intent(MainActivity.this, HabitsView.class);
            if (!new AuthManager(getApplicationContext()).isUserLoggedIn()) {
                myIntent = new Intent(MainActivity.this, LoginView.class);
            }
            MainActivity.this.startActivity(myIntent);
//            finish();
        }
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

    public static void test() {
        HabitDTO dto = HabitFactory.createDTO(
                1,
                "",
                "",
                1f,
                true,
                1,
                1,
                HabitType.GOOD_INTERVAL,
                new HabitInterval(3, HabitTimeUnit.HOUR)
        );
        System.out.println(OffsetDateTime.now());
        Habit h = HabitFactory.createFromDTO(dto);
        System.out.println(h.getNextDeadlineAt());
    }
}

