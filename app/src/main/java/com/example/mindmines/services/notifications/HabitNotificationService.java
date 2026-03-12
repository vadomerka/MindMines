package com.example.mindmines.services.notifications;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.content.ContextCompat;

import com.example.mindmines.models.habits.Habit;
import com.example.mindmines.models.NotifiBroadcastReciever;

import java.time.OffsetDateTime;
import java.util.Calendar;

public class HabitNotificationService {
    private static final String ALARM_ACTION = "com.example.mindmines.ALARM_TRIGGERED";
    private static final int REQUEST_CODE = 1001;

    public static void scheduleDailyAlarm(Context context, Habit h) {
        AlarmManager alarmManager = ContextCompat.getSystemService(context, AlarmManager.class);
        if (alarmManager == null) return;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (!alarmManager.canScheduleExactAlarms()) {
                requestPermission(context);
                return;
            }
        }

        Intent intent = new Intent(context, NotifiBroadcastReciever.class);
        intent.setAction(ALARM_ACTION);
        intent.putExtra("habitId", h.getHabitId());

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context,
                0,
                intent,
                PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_CANCEL_CURRENT
        );


        OffsetDateTime tNow = OffsetDateTime.now();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, tNow.getHour());
        calendar.set(Calendar.MINUTE, tNow.getMinute() + 1);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        if (calendar.getTimeInMillis() < System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(),
                pendingIntent
        );
    }

    private static void requestPermission(Context context) {
        Intent settingsIntent = new Intent(android.provider.Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM);
        settingsIntent.setData(android.net.Uri.parse("package:" + context.getPackageName()));
        context.startActivity(settingsIntent);
    }
}
