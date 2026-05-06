package com.example.mindmines.services.factories;

import android.content.Context;

import com.example.mindmines.db.entities.ExpeditionEntity;
import com.example.mindmines.db.entities.HabitEntity;
import com.example.mindmines.models.game.expeditions.Expedition;
import com.example.mindmines.models.habits.Habit;

public class FactoryService {
    private static CharFactory chF = null;
    private static HabitFactory hF = null;
    private static ExpeditionFactory eF = null;
    private static ChatMessageFactory mF = null;
    private static UserStatusFactory usF = null;

    public static CharFactory getCharFactory() {
        if (chF == null) chF = new CharFactory();
        return chF;
    }

    public static RepFactory<Integer, Habit, HabitEntity> get(Habit t) {
        if (hF == null) hF = new HabitFactory();
        return hF;
    }

    public static RepFactory<Integer, Expedition, ExpeditionEntity> get(Expedition t) {
        if (eF == null) eF = new ExpeditionFactory();
        return eF;
    }

    public static ChatMessageFactory getChatMessageFactory() {
        if (mF == null) mF = new ChatMessageFactory();
        return mF;
    }

    public static UserStatusFactory getUserStatusFactory() {
        if (usF == null) usF = new UserStatusFactory();
        return usF;
    }
}
