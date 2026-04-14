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

import com.example.mindmines.db.MindMinesDatabase;
import com.example.mindmines.db.datasync.CharDataSynchronizer;
import com.example.mindmines.db.datasync.DataSynchronizerManager;
import com.example.mindmines.db.datasync.HabitDataSynchronizer;
import com.example.mindmines.models.game.Char;
import com.example.mindmines.models.game.equipment.CharEquipment;
import com.example.mindmines.models.game.equipment.types.BodyArmor;
import com.example.mindmines.models.game.equipment.types.LegArmor;
import com.example.mindmines.models.game.equipment.types.Shield;
import com.example.mindmines.models.game.equipment.types.Sword;
import com.example.mindmines.services.factories.CharFactory;
import com.example.mindmines.services.repositories.CharRepository;
import com.example.mindmines.services.timers.DataBackupTimer;
import com.example.mindmines.services.timers.HabitStatusCheckerTimer;
import com.example.mindmines.services.auth.AuthManager;
import com.example.mindmines.services.repositories.HabitRepository;
import com.example.mindmines.views.habit.HabitsView;
import com.example.mindmines.views.user.LoginView;
import com.google.gson.Gson;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Debug start finish";
    private static final boolean DEBUG = false;
    private static DataSynchronizerManager dbSync;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");

        if (DEBUG) {
        } else {
            // TODO: change to loading from server.
            HabitRepository.init();
            CharRepository.init();
            dbSync = DataSynchronizerManager.getInstance(this);
            dbSync.loadFromDB();

            // Notifications init
            createNotificationChannel();
            // Timers init
            HabitStatusCheckerTimer.ensureScheduled(getApplicationContext());
            DataBackupTimer.ensureScheduled(getApplicationContext());

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

    public void test2() {
        HabitRepository.init();

        String tag = "DEBUG main";
        Log.d(tag, "db init");
        MindMinesDatabase db = MindMinesDatabase.getInstance(this);
        Log.d(tag, "data sync init");
        HabitDataSynchronizer sync = new HabitDataSynchronizer(this);
        Log.d(tag, "saveFromRepository");
        sync.saveToDB();
        Log.d(tag, "loadIntoRepository");
        sync.loadFromDB();
    }

    public void test3() {
        Char ch = CharFactory.generate();
        ch.setEquipment(new CharEquipment(new Sword(), new Shield(), new BodyArmor(), new LegArmor()));
        Gson g = new Gson();
        String j1 = g.toJson(ch);
        Char ch2 = g.fromJson(j1, Char.class);
        System.out.println("finish");
    }

    public void test4() {
        CharRepository.init();
        List<Char> chars1 = CharRepository.getAll();
        System.out.println(chars1.size());
        CharDataSynchronizer dSync = new CharDataSynchronizer(this);
        dSync.saveToDB();

        dSync.loadFromDB();
        List<Char> chars2 = CharRepository.getAll();
        System.out.println(chars2.size());
    }
}

