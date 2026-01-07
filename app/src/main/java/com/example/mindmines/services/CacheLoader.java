package com.example.mindmines.services;

import com.example.mindmines.models.UserSettings;

public class CacheLoader {
    public static UserSettings loadSettings() {
        return new UserSettings();
    }
}
