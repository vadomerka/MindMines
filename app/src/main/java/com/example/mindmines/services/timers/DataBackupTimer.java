package com.example.mindmines.services.timers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.example.mindmines.db.datasync.DataSynchronizerManager;
import com.example.mindmines.services.checkers.HabitSyncCheckerService;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DataBackupTimer extends BroadcastReceiver {
    private static final String TAG = "Debug data sync";
    private static final String ACTION_CHECK = "com.example.mindmines.DATA_BACKUP";
    private static final int REQUEST_CODE = 2000;
    private static final ExecutorService executor = Executors.newSingleThreadExecutor();

    @Override
    public void onReceive(Context context, Intent intent) {
        if (!ACTION_CHECK.equals(intent.getAction())) return;

        final PendingResult pendingResult = goAsync();

        new Thread(() ->
            executor.execute(() -> {
                try {
                    saveData(context);
                    scheduleNextCheck(context);
                } finally {
                    pendingResult.finish();
                }
            }
        )).start();
    }

    private static void saveData(Context context) {
        DataSynchronizerManager.getInstance(context).saveToDB();
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
                .plusMinutes(5)
                .withSecond(0)
                .withNano(0)
                .atZone(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli();
    }

    private void scheduleNextCheck(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager == null) return;

        long triggerAt = getNextMinuteStart();

        Intent intent = new Intent(context, DataBackupTimer.class);
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
    }

    public static void ensureScheduled(Context context) {
        Log.d(TAG, "ensureScheduled: DataBackupTimer");

        saveData(context);

        Intent intent = new Intent(context, DataBackupTimer.class);
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
            new DataBackupTimer().scheduleNextCheck(context);
        }
    }
}