package com.example.mindmines.services.checkers;

import android.content.Context;

import com.example.mindmines.infrastructure.HabitController;
import com.example.mindmines.models.habits.Habit;
import com.example.mindmines.models.habits.HabitButtonStatus;
import com.example.mindmines.models.habits.HabitTimeUnit;
import com.example.mindmines.services.managers.UserStatusManager;
import com.example.mindmines.services.repositories.RepositoryService;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;

public class HabitSyncCheckerService extends BasicChecker {
    private static HabitButtonStatus intervalAfterDeadline(Habit h) {
        OffsetDateTime last = h.getLastCompletedAt();
        OffsetDateTime n = OffsetDateTime.now();
        OffsetDateTime ded = h.getNextDeadlineAt();
        OffsetDateTime s = h.getPeriodStart();
        if (n.isBefore(ded)) {
            return HabitButtonStatus.TOO_EARLY_TO_CHECK;
        }
        if (ALWAYS_CHECKED) {
            return HabitButtonStatus.CHECKED;
        }
        if (last == null) {
            return HabitButtonStatus.NOT_CHECKED;
        }
        if (last.isBefore(s)) {
            return HabitButtonStatus.NOT_CHECKED;
        }
        return HabitButtonStatus.CHECKED;
    }

    private static HabitButtonStatus goalCountAfterDeadline(Habit h) {
        OffsetDateTime n = OffsetDateTime.now();
        OffsetDateTime ded = h.getNextDeadlineAt();
        if (n.isBefore(ded)) {
            return HabitButtonStatus.TOO_EARLY_TO_CHECK;
        }
        if (ALWAYS_CHECKED) {
            return HabitButtonStatus.CHECKED;
        }
        int goal = HabitCurrentCheckerService.normalizedGoal(h);
        int curr = HabitCurrentCheckerService.normalizedCurr(h);
        if (curr >= goal) {
            return HabitButtonStatus.CHECKED;
        }
        return HabitButtonStatus.NOT_CHECKED;
    }

    private static void syncIntervalHabit(Habit h, Context context) {
        UserStatusManager usm = UserStatusManager.getInstance(context);
        if (!h.getNextDeadlineAt().isBefore(OffsetDateTime.now())) {
            return;
        }

        HabitButtonStatus whu = intervalAfterDeadline(h);
        if (whu == HabitButtonStatus.CHECKED) {
            if (ALWAYS_CHECKED) {
                h.setStreakNumber(h.getStreakNumber() + 1);
                h.setPenaltyNumber(0);
                usm.gain(h);
            }
            h.setNextDeadlineAt(h.getNextNextDeadline(1));
        }

        OffsetDateTime d = h.getNextDeadlineAt();
        OffsetDateTime n = OffsetDateTime.now();

        Duration dur = Duration.between(d, n);
        long missed;
        if (h.getInterval().getTimeUnit() == HabitTimeUnit.MINUTE) {
            missed = dur.toMinutes();
        } else {
            missed = dur.toDays();
        }
        missed /= h.getInterval().getNumber();
        if (missed > 0) {
            h.setStreakNumber(0);
            h.setPenaltyNumber(h.getPenaltyNumber() + (int) missed);
            usm.gain(h);
            h.setNextDeadlineAt(h.getNextNextDeadline((int) missed));
        }

        while (h.getNextDeadlineAt().isBefore(OffsetDateTime.now())) {
            h.setStreakNumber(0);
            h.setPenaltyNumber(h.getPenaltyNumber() + 1);
            usm.gain(h);
            h.setNextDeadlineAt(h.getNextNextDeadline(1));
        }
    }

    private static void syncGoalCountHabit(Habit h, Context context) {
        UserStatusManager usm = UserStatusManager.getInstance(context);
        if (!h.getNextDeadlineAt().isBefore(OffsetDateTime.now())) {
            return;
        }

        HabitButtonStatus whu = goalCountAfterDeadline(h);
        if (whu == HabitButtonStatus.CHECKED) {
            if (ALWAYS_CHECKED) {
                h.setStreakNumber(h.getStreakNumber() + 1);
                h.setPenaltyNumber(0);
                usm.gain(h);
            }
            h.setNextDeadlineAt(h.getNextNextDeadline(1));
            h.setCurrCount(0);
        }

        OffsetDateTime d = h.getNextDeadlineAt();
        OffsetDateTime n = OffsetDateTime.now();

        Duration dur = Duration.between(d, n);
        long missed;
        if (h.getInterval().getTimeUnit() == HabitTimeUnit.MINUTE) {
            missed = dur.toMinutes();
        } else {
            missed = dur.toDays();
        }
        missed /= h.getInterval().getNumber();
        if (missed > 0) {
            h.setStreakNumber(0);
            h.setPenaltyNumber(h.getPenaltyNumber() + (int) missed);
            usm.gain(h);
            h.setNextDeadlineAt(h.getNextNextDeadline((int) missed));
            h.setCurrCount(0);
        }

        while (h.getNextDeadlineAt().isBefore(OffsetDateTime.now())) {
            h.setStreakNumber(0);
            h.setPenaltyNumber(h.getPenaltyNumber() + 1);
            usm.gain(h);
            h.setNextDeadlineAt(h.getNextNextDeadline(1));
            h.setCurrCount(0);
        }
    }

    private static void habitStatusCheck(Habit h, Context context) {
        switch (h.getType()) {
            case GOOD_INTERVAL:
                syncIntervalHabit(h, context);
                break;
            case GOOD_GOAL_COUNT:
                syncGoalCountHabit(h, context);
                break;
            case GOOD_TASKS:
            case BAD:
            default:
                break;
        }

        HabitController.getInstance(context).update(h);
    }

    public static void allHabitsCheck(Context context) {
        List<Habit> hl = RepositoryService.getHabitRepository().getByUser();
        for (Habit h : hl) {
            habitStatusCheck(h, context);
        }
        RepositoryService.getUserStatusRepository().updateObservers();
    }
}
