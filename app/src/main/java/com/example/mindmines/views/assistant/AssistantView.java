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
import com.example.mindmines.services.managers.ChatManager;
import com.example.mindmines.services.observers.ChatMessageObserver;
import com.example.mindmines.services.repositories.RepositoryService;
import com.example.mindmines.services.repositories.dao.ChatMessageRepository;
import com.example.mindmines.requests.ChatMessagesRequestSender;
import com.example.mindmines.views.BaseFragment;
import com.example.mindmines.views.adapters.ChatAdapter;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class AssistantView extends BaseFragment implements ChatMessageObserver {

    private ChatMessageFactory factory;
    private ChatManager manager;
    private ChatMessageRepository rep;
    private String userID;

    private RecyclerView chatRecyclerView;
    private EditText messageInput;
    private ProgressBar typingIndicator;
    private ChatAdapter chatAdapter;
    private List<ChatMessage> messageList;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());


    public AssistantView() {
        super(R.layout.assistant_chat_view);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initVars();
        initRecyclerView();
        initButtons();
        initMessageInput();

        Button navButton = requireView().findViewById(R.id.bottom_navigation_bar2);
        if (navButton != null) {
            navButton.setEnabled(false);
        }
    }

    private void initVars() {
        factory = ChatMessageFactory.getInstance();
        manager = new ChatManager();
        rep = RepositoryService.getChatMessageRepository();
        userID = new AuthManager(requireContext()).getUserId();
    }

    private void initRecyclerView() {
        chatRecyclerView = requireView().findViewById(R.id.chat_recycler_view);
        chatRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        messageList = manager.getMessages();
        chatAdapter = new ChatAdapter(messageList, requireContext());
        chatRecyclerView.setAdapter(chatAdapter);
    }

    private void initButtons() {
        ImageButton sendButton = requireView().findViewById(R.id.send_button);
        sendButton.setOnClickListener(v -> sendMessage());

        ImageButton deleteButton = requireView().findViewById(R.id.chat_reset_button);
        deleteButton.setOnClickListener(v -> deleteMessages());
    }

    private void initMessageInput() {
        messageInput = requireView().findViewById(R.id.message_input);
        typingIndicator = requireView().findViewById(R.id.typing_indicator);
        messageInput.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == android.view.inputmethod.EditorInfo.IME_ACTION_SEND) {
                sendMessage();
                return true;
            }
            return false;
        });
    }

    private void sendMessage() {
        String text = messageInput.getText().toString().trim();
        if (text.isEmpty()) return;

        ChatMessage userMessage = factory.create(userID,"USER", "CHAT", text);
        manager.addMessage(userMessage);

        messageInput.setText("");

        typingIndicator.setVisibility(View.VISIBLE);

        executor.execute(() -> {
            String response = ChatMessagesRequestSender.sendToServer(text, userID);
            mainHandler.post(() -> {
                typingIndicator.setVisibility(View.GONE);
                if (response != null) {
                    ChatMessage botMessage = factory.create(userID,"BOT", "CHAT", response);
                    manager.addMessage(botMessage);
                } else {
                    ChatMessage errorMessage = factory.create(userID,"ERROR", "CHAT", "Извините, произошла ошибка. Попробуйте позже.");
                    manager.addMessage(errorMessage);
                }
            });
        });
    }

    private void deleteMessages() {
        manager.removeAllMessages();
    }

    @Override
    public void update(List<ChatMessage> upd) {
        if (upd == null || upd.isEmpty()) {
            chatAdapter.notifyItemRangeRemoved(0, messageList.size());
            messageList = manager.getMessages();
        } else {
            ChatMessage cm = upd.get(0);
            chatAdapter.notifyItemInserted(messageList.size() - 1);
            messageList.add(cm);
            chatRecyclerView.smoothScrollToPosition(messageList.size() - 1);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        rep.subscribe(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        rep.unsubscribe(this);
    }
}