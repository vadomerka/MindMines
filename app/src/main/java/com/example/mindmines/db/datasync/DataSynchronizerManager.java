package com.example.mindmines.db.datasync;

import android.content.Context;
import android.util.Log;

import com.example.mindmines.services.managers.UserStatusManager;

import java.util.ArrayList;
import java.util.List;

public class DataSynchronizerManager {
    private static final String TAG = "Debug data sync";
    private final List<DataSynchronizer> synchronizers = new ArrayList<>();
    private static DataSynchronizerManager instance = null;

    private DataSynchronizerManager(Context context) {
        synchronizers.add(new HabitDataSynchronizer(context));
        UserStatusSynchronizer uss = new UserStatusSynchronizer(context);
        synchronizers.add(uss);
        UserStatusManager.subscribe(uss);
    }

    public static DataSynchronizerManager getInstance(Context context) {
        if (instance == null) {
            instance = new DataSynchronizerManager(context);
        }
        return instance;
    }

    public void loadFromDB() {
        for (int i = 0; i < synchronizers.size(); i++) {

            synchronizers.get(i).loadFromDB();
        }
    }

    public void saveToDB() {
        for (int i = 0; i < synchronizers.size(); i++) {
            Log.d(TAG, String.format("saveToDB: %d", i));
            synchronizers.get(i).saveToDB();
        }
    }
}
