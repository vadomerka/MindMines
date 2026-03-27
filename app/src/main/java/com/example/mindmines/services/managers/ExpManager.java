package com.example.mindmines.services.managers;

import android.util.Log;

import com.example.mindmines.models.UserStatus;
import com.example.mindmines.models.habits.Habit;

public class ExpManager {
    private static final long baseExpForHabit = 10L;
    private static final long baseMaxExpChange = 10;
    private static final double maxExpChangeKoef = 1.5;

    public static long getBaseMaxExpChange() { return baseMaxExpChange; }

    public static Long gainExp(UserStatus status, Habit h) {
        // Формула получения опыта - опыт увеличивается пропорционально стрику.
        long streakExp = baseExpForHabit * h.getStreakNumber() * h.getPriority() * h.getDifficulty();
        // Формула получения штрафа - штраф не увеличивается пропорционально,
        // лишь капает каждый раз когда идет пропуск.
        long penaltyExp = baseExpForHabit * h.getPriority() / h.getDifficulty();
        if (h.getPenaltyNumber() == 0) { penaltyExp = 0L; }
        Long change = streakExp - penaltyExp;
        Log.d("Debug ExpManager", String.format("streak: %s; penalty: %s", streakExp, penaltyExp));
        return status.getExperience() + change;
    }

    public static void gainLevel(UserStatus status, Long exp) {
        Integer level = status.getLevel();
        Long maxExp = status.getMaxExperience();
        if (exp < 0) {
            status.setExperience(0L);
            return;
        }
        while (exp > maxExp) {
            exp -= maxExp;
            status.setExperience(exp);
            if (level >= 30) { level = 30; }
            else {
                level++;
                maxExp = (long)(baseMaxExpChange * level * maxExpChangeKoef);
            }
        }
        status.setMaxExperience(maxExp);
        status.setLevel(level);
    }
}
