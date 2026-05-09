package com.example.mindmines.requests;

public class ServerProperties {
    public String SERVER_URL = "http://10.0.2.2:8000";
    public final String ASSISTANT_API = "/api/chat";
    public final String REGISTER_URL = "/users/register";
    public final String LOGIN_URL = "/users/login";

    private static ServerProperties instance;
    public ServerProperties() {}

    public static ServerProperties getInstance() {
        if (instance == null) {
            instance = new ServerProperties();
        }
        return instance;
    }
}
