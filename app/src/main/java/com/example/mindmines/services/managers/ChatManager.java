package com.example.mindmines.services.managers;

import com.example.mindmines.models.chat.ChatMessage;
import com.example.mindmines.services.repositories.RepositoryService;
import com.example.mindmines.services.repositories.dao.ChatMessageRepository;

import java.util.List;

public class ChatManager {
    ChatMessageRepository rep;

    public ChatManager() {
        rep = RepositoryService.getChatMessageRepository();
    }

    public List<ChatMessage> getMessages() {
        return rep.getByUser();
    }

    public void addMessage(ChatMessage message) {
        rep.add(message);
    }

    public void removeAllMessages() {
        for (ChatMessage cm : rep.getByUser()) {
            rep.remove(cm);
        }
    }
}
