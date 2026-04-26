package com.example.mindmines.views.assistant;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mindmines.R;
import com.example.mindmines.models.chat.ChatMessage;
import com.example.mindmines.views.BaseActivity;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AssistantView extends BaseActivity {

    private RecyclerView chatRecyclerView;
    private EditText messageInput;
    private ImageButton sendButton;
    private ProgressBar typingIndicator;
    private ChatAdapter chatAdapter;
    private List<ChatMessage> messageList = new ArrayList<>();
    private ExecutorService executor = Executors.newSingleThreadExecutor();
    private Handler mainHandler = new Handler(Looper.getMainLooper());

    // URL вашего серверного эндпоинта
    private static final String SERVER_URL = "https://your-api.com/chat";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Инициализация view
        chatRecyclerView = findViewById(R.id.chat_recycler_view);
        messageInput = findViewById(R.id.message_input);
        sendButton = findViewById(R.id.send_button);
        typingIndicator = findViewById(R.id.typing_indicator);

        // Настройка RecyclerView
        chatRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        chatAdapter = new ChatAdapter(messageList);
        chatRecyclerView.setAdapter(chatAdapter);

        // Обработчик кнопки отправки
        sendButton.setOnClickListener(v -> sendMessage());

        // Также можно отправлять по Enter (опционально)
        messageInput.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == android.view.inputmethod.EditorInfo.IME_ACTION_SEND) {
                sendMessage();
                return true;
            }
            return false;
        });

        // Отключаем кнопку навигации (уже есть в вашем коде)
        Button navButton = findViewById(R.id.bottom_navigation_bar2);
        if (navButton != null) {
            navButton.setEnabled(false);
        }
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.assistant_chat_view;
    }

    @Override
    protected Context getCurrentContext() {
        return AssistantView.this;
    }

    private void sendMessage() {
        String text = messageInput.getText().toString().trim();
        if (text.isEmpty()) return;

        // Добавляем сообщение пользователя в список
        ChatMessage userMessage = new ChatMessage(text, true);
        messageList.add(userMessage);
        chatAdapter.notifyItemInserted(messageList.size() - 1);
        chatRecyclerView.smoothScrollToPosition(messageList.size() - 1);
        messageInput.setText("");

        // Показываем индикатор печати
        typingIndicator.setVisibility(View.VISIBLE);

        // Отправляем запрос на сервер в фоновом потоке
        executor.execute(() -> {
            String response = sendToServer(text);
            mainHandler.post(() -> {
                typingIndicator.setVisibility(View.GONE);
                if (response != null) {
                    ChatMessage botMessage = new ChatMessage(response, false);
                    messageList.add(botMessage);
                    chatAdapter.notifyItemInserted(messageList.size() - 1);
                    chatRecyclerView.smoothScrollToPosition(messageList.size() - 1);
                } else {
                    // Обработка ошибки
                    ChatMessage errorMessage = new ChatMessage("Извините, произошла ошибка. Попробуйте позже.", false);
                    messageList.add(errorMessage);
                    chatAdapter.notifyItemInserted(messageList.size() - 1);
                }
            });
        });
    }

    private String sendToServer(String userMessage) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(SERVER_URL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);

            // Формируем JSON-тело запроса
            JSONObject requestBody = new JSONObject();
            requestBody.put("message", userMessage);

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

                // Парсим ответ (предполагаем, что сервер возвращает {"reply": "ответ"})
                JSONObject jsonResponse = new JSONObject(response.toString());
                return jsonResponse.optString("reply", "Пустой ответ");
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    // Адаптер RecyclerView


}