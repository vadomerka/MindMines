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

    public static String getDayWord(long time) {
        if (time % 10 == 1 && time % 100 != 11) return "день";
        if (time % 10 >= 2 && time % 10 <= 4 && (time % 100 < 10 || time % 100 >= 20)) return "дня";
        return "дней";
    }

    public static String getWeekWord(long time) {
        if (time % 10 == 1 && time % 100 != 11) return "неделя";
        if (time % 10 >= 2 && time % 10 <= 4 && (time % 100 < 10 || time % 100 >= 20)) return "недели";
        return "неделей";
    }

    public static String getHourWord(long time) {
        if (time % 10 == 1 && time % 100 != 11) return "час";
        if (time % 10 >= 2 && time % 10 <= 4 && (time % 100 < 10 || time % 100 >= 20)) return "часа";
        return "часов";
    }

    public static String getMinuteWord(long time) {
        if (time % 10 == 1 && time % 100 != 11) return "минута";
        if (time % 10 >= 2 && time % 10 <= 4 && (time % 100 < 10 || time % 100 >= 20)) return "минуты";
        return "минут";
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

    public static String parsePresetDuration(Duration duration) {
        long days = duration.toDays();
        if (days > 0) { return days + " " + getDayWord(days); }
        long hours = duration.toHours();
        if (hours > 0) { return days + " " + getHourWord(hours); }
        long minutes = duration.toMinutes() - hours * 60;
        if (minutes > 0) { return days + " " + getMinuteWord(minutes); }
        return "0 минут";
    }
}
