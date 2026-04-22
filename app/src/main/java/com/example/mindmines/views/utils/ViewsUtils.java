package com.example.mindmines.views.utils;

import android.annotation.SuppressLint;

import java.time.Duration;
import java.util.Locale;

public class ViewsUtils {
    @SuppressLint("DefaultLocale")
    public static String intToString(int d) {
        return String.format("%d", d);
    }

    public static int floatToInt(float d) {
        return (int) d;
    }

    @SuppressLint("DefaultLocale")
    public static String floatToString(float d) {
        return String.format("%f", d);
    }

    public static String getDayWord(long days) {
        if (days % 10 == 1 && days % 100 != 11) return "день";
        if (days % 10 >= 2 && days % 10 <= 4 && (days % 100 < 10 || days % 100 >= 20)) return "дня";
        return "дней";
    }

    public static String parseDuration(Duration duration) {
        long days = duration.toDays();
        String timeText;
        if (days > 0) {
            timeText = days + " " + ViewsUtils.getDayWord(days);
        } else {
            long hours = duration.toHours();
            long minutes = duration.toMinutes() - hours * 60;
            long seconds = (duration.toMillis() / 1000) - duration.toMinutes() * 60;
            timeText = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds);
        }
        return timeText;
    }
}
