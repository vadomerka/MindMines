package com.example.mindmines.services.utils;

import android.annotation.SuppressLint;

public class UIUtils {
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
}
