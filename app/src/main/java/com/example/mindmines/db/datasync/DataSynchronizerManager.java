package com.example.mindmines.db.datasync;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class DataSynchronizerManager {
    private static final String TAG = "Debug data sync";
    private final List<DataSynchronizer> synchronizers = new ArrayList<>();
    private static DataSynchronizerManager instance = null;

    private DataSynchronizerManager(Context context) {
        synchronizers.add(new HabitDataSynchronizer(context));
        synchronizers.add(new UserStatusSynchronizer(context));
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
            synchronizers.get(i).saveToDB();
        }
    }
}
