package com.example.mindmines.services.senders;

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

public class ChatMessagesSender {
    private static HttpURLConnection connection;
    private static final String SERVER_URL = "http://10.0.2.2:8000/api/chat";

    public static String sendToServer(String userMessage, String userToken) {
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
            e.printStackTrace();  // java.net.ConnectException: Failed to connect to /127.0.0.1:8000
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return null;
    }

    private static void initConnection() throws IOException {
        URL url = new URL(SERVER_URL);
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        connection.setRequestProperty("Accept", "application/json");
        connection.setDoOutput(true);
        connection.setConnectTimeout(10000);
        connection.setReadTimeout(10000);
    }

    private static String parseResponse(JSONObject requestBody) throws IOException, JSONException {
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
        } else {
            return null;
        }
    }
}
