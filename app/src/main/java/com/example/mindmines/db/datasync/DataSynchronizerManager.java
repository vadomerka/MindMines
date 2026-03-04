package com.example.mindmines.db.datasync;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class DataSynchronizerManager {
    private static final String TAG = "Debug data sync";
    private final List<DataSynchronizer> synchronizers = new ArrayList<>();

    public DataSynchronizerManager(Context context) {
        synchronizers.add(new HabitDataSynchronizer(context));
    }

    public void loadFromDB() {
        for (DataSynchronizer ds: synchronizers) {
            ds.loadFromDB();
        }
    }

    public void saveToDB() {
        for (DataSynchronizer ds: synchronizers) {
            ds.saveToDB();
        }
    }
}
