package com.example.mindmines.services.notifications;

import android.Manifest;
import android.app.AlarmManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.mindmines.models.habits.Habit;
import com.example.mindmines.services.repositories.RepositoryService;

public class NotifiBroadcastReciever extends BroadcastReceiver {
    private static final String CHANNEL_ID = "habit_channel";
    private static final int NOTIFICATION_ID = 100;
    private static final long REPEAT_INTERVAL = AlarmManager.INTERVAL_DAY;

    @Override
    public void onReceive(Context context, Intent intent) {
        Habit h = RepositoryService.getHabitRepository().get(intent.getIntExtra("habitId", -1));

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(String.format("Напоминание о %s", h.getTitle()))
                .setContentText("Не забудьте выполнить сегодняшнюю цель!")
                .setContentInfo("info setter")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, builder.build());

        HabitNotificationService.scheduleDailyAlarm(context, h);
    }
}