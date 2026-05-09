package com.example.mindmines.services.managers;

import android.util.Log;

import com.example.mindmines.models.XpStatus;
import com.example.mindmines.models.game.characters.Char;
import com.example.mindmines.models.game.expeditions.Expedition;
import com.example.mindmines.models.habits.Habit;
import com.example.mindmines.models.user.UserStatus;
import com.example.mindmines.services.observers.ExpeditionObserver;

import java.time.Duration;
import java.util.List;

public class XpManager {
    private static final long baseExpForHabit = 10L;
    private static final long baseExpForEx = 10L;
    private static final long baseMaxExpChange = 10;
    private static final long minCoins = 1;
    private static final double maxExpChangeKoef = 1.5;
    private static final int maxLevel = 30;

    public static long getBaseMaxExpChange() { return baseMaxExpChange; }

    public static Long habitToExp(Habit h) {  // XpStatus status,
        // Формула получения опыта - опыт увеличивается пропорционально стрику.
        long streakExp = baseExpForHabit * h.getStreakNumber() * h.getPriority() * h.getDifficulty();
        // Формула получения штрафа - штраф не увеличивается пропорционально,
        // лишь капает каждый раз когда идет пропуск.
        long penaltyExp = baseExpForHabit * h.getPriority() / h.getDifficulty();
        if (h.getPenaltyNumber() == 0) { penaltyExp = 0L; }
        Long change = streakExp - penaltyExp;
        Log.d("Debug ExpManager", String.format("streak: %s; penalty: %s", streakExp, penaltyExp));
        return change;  // status.getExperience() +
    }

    public static Long expeditionToRewards(Expedition ex) {  // XpStatus status,
        int level = ex.getLevel();
        Duration d = Duration.between(ex.getStart(), ex.getFinish());
        return minCoins + level * (d.getSeconds() / 3600);
    }

    public static Long expeditionToCharExp(Char ch, Expedition ex) {  // XpStatus status,
        int chLevel = ch.getStatus().getLevel();
        int exLevel = ex.getLevel();
        int levelDif = exLevel - chLevel;
        long res = baseExpForEx;
        if (levelDif > 0) res *= levelDif;
        else if (levelDif < 0) res /= Math.abs(levelDif);

        Duration d = Duration.between(ex.getStart(), ex.getFinish());
        return res * (d.getSeconds() / 3600);
    }

    public static void gainLevel(XpStatus status, Long exp) {
        Integer level = status.getLevel();
        Long maxExp = status.getMaxExperience();
        exp = status.getExperience() + exp;
        if (exp < 0) {
            status.setExperience(0L);
            return;
        }
        while (exp > maxExp) {
            exp -= maxExp;
            status.setExperience(exp);
            if (level >= maxLevel) { level = maxLevel; }
            else {
                level++;
                maxExp = (long)(baseMaxExpChange * level * maxExpChangeKoef);
            }
        }
        status.setExperience(exp);
        status.setMaxExperience(maxExp);
        status.setLevel(level);
    }
}
