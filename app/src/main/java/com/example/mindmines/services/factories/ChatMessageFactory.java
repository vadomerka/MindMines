package com.example.mindmines.services.factories;

import com.example.mindmines.db.entities.ChatMessageEntity;
import com.example.mindmines.models.chat.ChatMessage;
import com.example.mindmines.services.converters.entities.RepConverter;
import com.example.mindmines.services.repositories.dao.ChatMessageRepository;
import com.example.mindmines.services.repositories.RepositoryService;

import java.time.OffsetDateTime;

public class ChatMessageFactory {
    private final ChatMessageRepository rep;
    private static ChatMessageFactory instance;

    public ChatMessageFactory() {
        this.rep = RepositoryService.getChatMessageRepository();
    }

    public static ChatMessageFactory getInstance() {
        if (instance == null) {
            instance = new ChatMessageFactory();
        }
        return instance;
    }

    public ChatMessage create(String userId,
                              String author,
                              String type,
                              String body) {

        return new ChatMessage(
                rep.getId() + 1,
                userId,
                author,
                type,
                "",
                body,
                OffsetDateTime.now(),
                null
        );
    }
}
