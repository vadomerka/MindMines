package com.example.mindmines.requests;

import com.example.mindmines.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class ChatMessagesRequestSender {
    private HttpURLConnection connection;
    private final String SERVER_URL;
    private final String ASSISTANT_API;
    private static ChatMessagesRequestSender instance;

    public ChatMessagesRequestSender() {
        SERVER_URL = ServerProperties.getInstance().SERVER_URL;
        ASSISTANT_API = ServerProperties.getInstance().ASSISTANT_API;
    }

    public static ChatMessagesRequestSender getInstance() {
        if (instance == null) {
            instance = new ChatMessagesRequestSender();
        }
        return instance;
    }

    public String sendToServer(String userMessage, String userToken) {
        try {
            initConnection();

            JSONArray messagesArray = new JSONArray();
            JSONObject messageObj = new JSONObject();
            messageObj.put("role", "user");
            messageObj.put("content", userMessage);
            messageObj.put("userToken", userToken);
            messageObj.put("chatId", userToken);  // В будущем будут реализованы разные чаты.
            messagesArray.put(messageObj);

            JSONObject requestBody = new JSONObject();
            requestBody.put("messages", messagesArray);

            return parseResponse(requestBody);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return null;
    }

    private void initConnection() throws IOException {
        URL url = new URL(SERVER_URL + ASSISTANT_API);
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
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            br.close();

            return new JSONObject(response.toString()).optString("reply", "Пустой ответ");
        }
        return null;
    }
}
