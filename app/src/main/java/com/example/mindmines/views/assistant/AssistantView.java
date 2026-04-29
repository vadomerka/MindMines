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
import com.example.mindmines.services.factories.ChatMessageFactory;
import com.example.mindmines.services.senders.ChatMessagesSender;
import com.example.mindmines.views.BaseActivity;
import com.example.mindmines.views.adapters.ChatAdapter;

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

import io.noties.markwon.Markwon;

public class AssistantView extends BaseActivity {

    private RecyclerView chatRecyclerView;
    private EditText messageInput;
    private ImageButton sendButton;
    private ProgressBar typingIndicator;
    private ChatAdapter chatAdapter;
    private List<ChatMessage> messageList = new ArrayList<>();
    private ExecutorService executor = Executors.newSingleThreadExecutor();
    private Handler mainHandler = new Handler(Looper.getMainLooper());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        chatRecyclerView = findViewById(R.id.chat_recycler_view);
        messageInput = findViewById(R.id.message_input);
        sendButton = findViewById(R.id.send_button);
        typingIndicator = findViewById(R.id.typing_indicator);

        chatRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        chatAdapter = new ChatAdapter(messageList, getCurrentContext());
        chatRecyclerView.setAdapter(chatAdapter);

        sendButton.setOnClickListener(v -> sendMessage());

        messageInput.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == android.view.inputmethod.EditorInfo.IME_ACTION_SEND) {
                sendMessage();
                return true;
            }
            return false;
        });

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

        ChatMessage userMessage = ChatMessageFactory.create("USER", "CHAT", text);
        messageList.add(userMessage);
        chatAdapter.notifyItemInserted(messageList.size() - 1);
        chatRecyclerView.smoothScrollToPosition(messageList.size() - 1);
        messageInput.setText("");

        typingIndicator.setVisibility(View.VISIBLE);

        executor.execute(() -> {
            String response = ChatMessagesSender.sendToServer(text);
            mainHandler.post(() -> {
                typingIndicator.setVisibility(View.GONE);
                String responseText;
                if (response != null) {
                    responseText = response;
                } else {
                    responseText = "Извините, произошла ошибка. Попробуйте позже.";
                }
                ChatMessage resMessage = ChatMessageFactory.create("BOT", "CHAT", responseText);

                messageList.add(resMessage);
                chatAdapter.notifyItemInserted(messageList.size() - 1);
                chatRecyclerView.smoothScrollToPosition(messageList.size() - 1);
            });
        });
    }


}