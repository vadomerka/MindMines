package com.example.mindmines.services.checkers;

import android.content.Context;
import android.graphics.Color;
import android.widget.Button;

import com.example.mindmines.R;
import com.example.mindmines.infrastructure.HabitController;
import com.example.mindmines.models.habits.Habit;
import com.example.mindmines.services.managers.UserStatusManager;

import java.time.OffsetDateTime;

public class HabitCurrentCheckerService extends BasicChecker {
    private static boolean isHabitUnchecked(Habit h) {
        if (ALWAYS_CHECKED) {
            return false;
        }
        switch (h.getType()) {
            case GOOD_GOAL_COUNT:
                return isGoalCountUnchecked(h);
            case GOOD_INTERVAL:
            case GOOD_TASKS:
            case BAD:
            default:
                return isIntervalUnchecked(h);
        }
    }

    private static boolean isIntervalUnchecked(Habit h) {
        OffsetDateTime last = h.getLastCompletedAt();
        OffsetDateTime n = OffsetDateTime.now();
        OffsetDateTime ded = h.getNextDeadlineAt();

        OffsetDateTime s = h.getPeriodStart();
        if (last == null) {
            return true;
        }

        if (ded != null && n.isAfter(ded)) {
            return true;
        }
        return last.isBefore(s);
    }

    private static boolean isGoalCountUnchecked(Habit h) {
        OffsetDateTime n = OffsetDateTime.now();
        OffsetDateTime ded = h.getNextDeadlineAt();
        if (ded != null && n.isAfter(ded)) {
            return true;
        }
        int goal = normalizedGoal(h);
        int curr = normalizedCurr(h);
        return curr < goal;
    }

    public static void buttonViewUpdate(Button btn) {
        Habit h = (Habit) btn.getTag();
        if (isHabitUnchecked(h)) {
            btn.setTextAppearance(R.style.UncheckedHabitButton);
            btn.setBackgroundColor(Color.parseColor("#D56363"));
        } else {
            btn.setTextAppearance(R.style.CheckedHabitButton);
            btn.setBackgroundColor(Color.parseColor("#6DFF8D"));
        }
    }

    public static void buttonStatusCheck(Button btn, Context context) {
        Habit h = (Habit) btn.getTag();

        switch (h.getType()) {
            case GOOD_GOAL_COUNT:
                goalCountTap(h, context);
                break;
            case GOOD_TASKS:
            default:
                intervalTap(h, context);
                break;
        }

        buttonViewUpdate(btn);

        HabitController.getInstance(context).update(h);
    }

    private static void intervalTap(Habit h, Context context) {
        if (isIntervalUnchecked(h)) {
            applyCompletionReward(h, context);
        }
    }

    private static void goalCountTap(Habit h, Context context) {
        if (!isGoalCountUnchecked(h)) {
            return;
        }
        int goal = normalizedGoal(h);
        int curr = normalizedCurr(h);
        if (curr >= goal) {
            return;
        }
        curr += 1;
        h.setCurrCount(curr);

        if (curr >= goal) {
            applyCompletionReward(h, context);
        }
    }

    private static void applyCompletionReward(Habit h, Context context) {
        h.setLastCompletedAt(OffsetDateTime.now());
        h.setStreakNumber(h.getStreakNumber() + 1);
        h.setPenaltyNumber(0);
        HabitController.getInstance(context).update(h);

        UserStatusManager.getInstance(context).gain(h);
    }

    public static int normalizedGoal(Habit h) {
        Integer g = h.getGoalCount();
        if (g == null || g < 1) {
            return 1;
        }
        return g;
    }

    public static int normalizedCurr(Habit h) {
        Integer c = h.getCurrCount();
        if (c == null || c < 0) {
            return 0;
        }
        return c;
    }
}
