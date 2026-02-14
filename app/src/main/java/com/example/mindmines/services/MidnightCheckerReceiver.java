package com.example.mindmines.services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MidnightCheckerReceiver extends BroadcastReceiver {
    private static final String TAG = "MidnightChecker";
    private static final String ACTION_CHECK = "com.example.mindmines.MIDNIGHT_CHECK";
    private static final int REQUEST_CODE = 2000;
    private static final ExecutorService executor = Executors.newSingleThreadExecutor();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, ">>> СРАБОТАЛ БУДИЛЬНИК в " + OffsetDateTime.now().toInstant().toEpochMilli());
        if (!ACTION_CHECK.equals(intent.getAction())) return;

        final PendingResult pendingResult = goAsync();

        new Thread(() ->
            executor.execute(() -> {
                try {
                    checkHabits(context);
                    scheduleNextCheck(context);
                } finally {
                    pendingResult.finish();
                }
            }
        )).start();
    }

    private void checkHabits(Context context) {
        HabitCheckerService.checkAllHabits();
    }

    private long getNextDayStart() {
        return LocalDateTime.now()
                .plusDays(1)
                .withHour(0)
                .withMinute(0)
                .withSecond(0)
                .withNano(0)
                .atZone(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli();
    }

    private long getNextMinuteStart() {
        return LocalDateTime.now()
                .plusMinutes(1)
                .withSecond(0)
                .withNano(0)
                .atZone(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli();
    }

    public static long nowMillis() {
        return LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    private void scheduleNextCheck(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager == null) return;

        long triggerAt = getNextMinuteStart();

        Intent intent = new Intent(context, MidnightCheckerReceiver.class);
        intent.setAction(ACTION_CHECK);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context,
                REQUEST_CODE,
                intent,
                PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_CANCEL_CURRENT
        );

        alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                triggerAt,
                pendingIntent
        );
//        Log.d(TAG, ">>> Следующий будильник: " + triggerAt);
    }

    public static void ensureScheduled(Context context) {
        Intent intent = new Intent(context, MidnightCheckerReceiver.class);
        intent.setAction(ACTION_CHECK);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context,
                REQUEST_CODE,
                intent,
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
                        ? PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_NO_CREATE
                        : PendingIntent.FLAG_NO_CREATE
        );

        if (pendingIntent == null) {
            Log.d(TAG, ">>> Будильник не найден — ставим новый");
            new MidnightCheckerReceiver().scheduleNextCheck(context);
        }
    }
}