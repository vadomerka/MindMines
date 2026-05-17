package com.example.mindmines.requests;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class AuthRequestSender {
    private static AuthRequestSender instance;
    private final String SERVER_URL;
    private final String REGISTER_URL;
    private final String LOGIN_URL;
    private HttpURLConnection connection;

    public AuthRequestSender() {
        SERVER_URL = ServerProperties.getInstance().SERVER_URL;
        REGISTER_URL = ServerProperties.getInstance().REGISTER_URL;
        LOGIN_URL = ServerProperties.getInstance().LOGIN_URL;
    }

    public static AuthRequestSender getInstance() {
        if (instance == null) {
            instance = new AuthRequestSender();
        }
        return instance;
    }

    public String registerRequestSend(String email, String password) throws JSONException, IOException {
        if (email == null || email.isEmpty()) throw new IllegalArgumentException("Empty email field");
        return sendAuthRequest(SERVER_URL + REGISTER_URL, email, password);
    }

    public String loginRequestSend(String email, String password) throws JSONException, IOException {
        if (email == null || email.isEmpty()) throw new IllegalArgumentException("Empty email field");
        return sendAuthRequest(SERVER_URL + LOGIN_URL, email, password);
    }

    private String sendAuthRequest(String endpoint, String email, String password) throws IOException, JSONException {
        try {
            initConnection(endpoint);

            JSONObject requestBody = new JSONObject();
            requestBody.put("email", email);
            requestBody.put("password", password);

            return parseResponse(requestBody);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private void initConnection(String endpoint) throws IOException {
        URL url = new URL(endpoint);
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        connection.setRequestProperty("Accept", "application/json");
        connection.setDoOutput(true);
        connection.setConnectTimeout(10000);
        connection.setReadTimeout(10000);
    }

    private String parseResponse(JSONObject requestBody) throws IOException, JSONException {
        OutputStream os = connection.getOutputStream();
        os.write(requestBody.toString().getBytes(StandardCharsets.UTF_8));
        os.close();

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            return parseToken(connection);
        }
        return null;
    }

    private String parseToken(HttpURLConnection connection) throws IOException, JSONException {
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            return new JSONObject(response.toString()).optString("user_token", null);
        }
    }
}
