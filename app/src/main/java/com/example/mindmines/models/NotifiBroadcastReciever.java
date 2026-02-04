package com.example.mindmines.models;

import android.Manifest;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.mindmines.MainActivity;
import com.example.mindmines.R;
import com.example.mindmines.services.notifications.HabitNotificationService;
import com.example.mindmines.services.repositories.HabitRepository;
import com.example.mindmines.views.habit.HabitAddView;

public class NotifiBroadcastReciever extends BroadcastReceiver {
    private static final String CHANNEL_ID = "habit_channel";
    private static final int NOTIFICATION_ID = 100;
    private static final long REPEAT_INTERVAL = AlarmManager.INTERVAL_DAY;

    @Override
    public void onReceive(Context context, Intent intent) {
        Habit h = HabitRepository.get(intent.getIntExtra("habitId", -1));

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(String.format("Напоминание о %s", h.getTitle()))
                .setContentText("Не забудьте выполнить сегодняшнюю цель!")
                .setContentInfo("info setter")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, builder.build());

//        boolean repeating = true;
//        if (repeating) {
//            HabitNotificationService.scheduleDailyAlarm(context, h);
//        }
        HabitNotificationService.scheduleDailyAlarm(context, h);
    }
}