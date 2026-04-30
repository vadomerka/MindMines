package com.example.mindmines.views.assistant;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mindmines.R;
import com.example.mindmines.models.chat.ChatMessage;
import com.example.mindmines.services.auth.AuthManager;
import com.example.mindmines.services.factories.ChatMessageFactory;
import com.example.mindmines.services.senders.ChatMessagesSender;
import com.example.mindmines.views.BaseFragment;
import com.example.mindmines.views.adapters.ChatAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class AssistantView extends BaseFragment {

    private ChatMessageFactory factory;
    private String userID;
    private RecyclerView chatRecyclerView;
    private EditText messageInput;
    private ImageButton sendButton;
    private ProgressBar typingIndicator;
    private ChatAdapter chatAdapter;
    private List<ChatMessage> messageList = new ArrayList<>();
    private ExecutorService executor = Executors.newSingleThreadExecutor();
    private Handler mainHandler = new Handler(Looper.getMainLooper());


    public AssistantView() {
        super(R.layout.assistant_chat_view);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        factory = ChatMessageFactory.getInstance();
        userID = new AuthManager(requireContext()).getUserId();

        chatRecyclerView = requireView().findViewById(R.id.chat_recycler_view);
        messageInput = requireView().findViewById(R.id.message_input);
        sendButton = requireView().findViewById(R.id.send_button);
        typingIndicator = requireView().findViewById(R.id.typing_indicator);

        chatRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        chatAdapter = new ChatAdapter(messageList);
        chatRecyclerView.setAdapter(chatAdapter);

        sendButton.setOnClickListener(v -> sendMessage());

        messageInput.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == android.view.inputmethod.EditorInfo.IME_ACTION_SEND) {
                sendMessage();
                return true;
            }
            return false;
        });

        Button navButton = requireView().findViewById(R.id.bottom_navigation_bar2);
        if (navButton != null) {
            navButton.setEnabled(false);
        }
    }

    private void sendMessage() {
        String text = messageInput.getText().toString().trim();
        if (text.isEmpty()) return;

        ChatMessage userMessage = factory.create(userID,"USER", "CHAT", text);
        messageList.add(userMessage);
        chatAdapter.notifyItemInserted(messageList.size() - 1);
        chatRecyclerView.smoothScrollToPosition(messageList.size() - 1);
        messageInput.setText("");

        typingIndicator.setVisibility(View.VISIBLE);

        executor.execute(() -> {
            String response = ChatMessagesSender.sendToServer(text);
            mainHandler.post(() -> {
                typingIndicator.setVisibility(View.GONE);
                if (response != null) {
                    ChatMessage botMessage = factory.create(userID,"BOT", "CHAT", response);
                    messageList.add(botMessage);
                    chatAdapter.notifyItemInserted(messageList.size() - 1);
                    chatRecyclerView.smoothScrollToPosition(messageList.size() - 1);
                } else {
                    ChatMessage errorMessage = factory.create(userID,"BOT", "CHAT", "Извините, произошла ошибка. Попробуйте позже.");
                    messageList.add(errorMessage);
                    chatAdapter.notifyItemInserted(messageList.size() - 1);
                }
            });
        });
    }


}