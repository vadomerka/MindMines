package com.example.mindmines.requests;

import com.example.mindmines.models.user.UserDTO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class UserRequestSender {
    private static UserRequestSender instance;
    private final String SERVER_URL;
    private final String FRIENDS_URL;
    private HttpURLConnection connection;

    public UserRequestSender() {
        SERVER_URL = ServerProperties.getInstance().SERVER_URL;
        FRIENDS_URL = ServerProperties.getInstance().FRIENDS_URL;
    }

    public static UserRequestSender getInstance() {
        if (instance == null) {
            instance = new UserRequestSender();
        }
        return instance;
    }

    public List<UserDTO> getFriendsRequestSend(String userId) {
        List<UserDTO> users = new ArrayList<>();
        try {
            initConnection(SERVER_URL + FRIENDS_URL);

            int responseCode = connection.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                return users;
            }
            return parseResponse();

        } catch (Exception e) {
            return users;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private void initConnection(String endpoint) throws IOException {
        URL url = new URL(endpoint);
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");
        connection.setConnectTimeout(10000);
        connection.setReadTimeout(10000);
    }

    private List<UserDTO> parseResponse() throws IOException, JSONException {
        List<UserDTO> users = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            JSONArray usersJson = new JSONArray(response.toString());
            for (int i = 0; i < usersJson.length(); i++) {
                JSONObject user = usersJson.getJSONObject(i);
                String name = user.optString("name", "");
                int level = user.optInt("level", 0);
                users.add(new UserDTO(name, level));
            }
        }
        return users;
    }
}
