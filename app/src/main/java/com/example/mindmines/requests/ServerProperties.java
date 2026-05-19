package com.example.mindmines.requests;

public class ServerProperties {
    private static ServerProperties instance;
    public final String ASSISTANT_API = "/api/chat";
    public final String REGISTER_URL = "/users/register";
    public final String LOGIN_URL = "/users/login";
    public final String FRIENDS_URL = "/users/friends";
    public String SERVER_URL = "http://10.0.2.2:8000";

    public ServerProperties() {
    }

    public static ServerProperties getInstance() {
        if (instance == null) {
            instance = new ServerProperties();
        }
        return instance;
    }
}
