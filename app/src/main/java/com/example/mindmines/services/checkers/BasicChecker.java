package com.example.mindmines.services.checkers;

public abstract class BasicChecker {
    protected static Boolean ALWAYS_CHECKED = false;
    public static boolean getDebug() { return ALWAYS_CHECKED; }
    public static void setDebug(boolean d) { ALWAYS_CHECKED = d; }
}
