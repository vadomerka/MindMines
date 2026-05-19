package com.example.mindmines.services.managers;

import android.content.Context;

import com.example.mindmines.models.game.expeditions.ExpeditionLocation;

public class ExpeditionLocationManager {
    private static ExpeditionLocationManager instance;
    private final Context context;
    private final CharManager chm;

    public ExpeditionLocationManager(Context context) {
        this.context = context;
        chm = CharManager.getInstance(context);
    }

    public static ExpeditionLocationManager getInstance(Context context) {
        if (instance == null) {
            instance = new ExpeditionLocationManager(context);
        }
        return instance;
    }

    public boolean isAvailable(ExpeditionLocation location) {
        return location.getLevel() <= chm.getMinLevel();
    }
}
