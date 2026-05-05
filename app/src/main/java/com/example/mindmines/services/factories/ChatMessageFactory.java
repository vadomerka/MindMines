package com.example.mindmines.services.factories;

import com.example.mindmines.models.chat.ChatMessage;
import com.example.mindmines.services.repositories.implementations.ChatMessageRepository;
import com.example.mindmines.services.repositories.RepositoryService;

import java.time.OffsetDateTime;
import java.util.OptionalInt;

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

    private int getId() {
        OptionalInt rm = rep.getAll() != null
                ? rep.getAll().stream().mapToInt(ChatMessage::getId).max()
                : OptionalInt.of(0);
        return (rm.isPresent() ? rm.getAsInt() : 0) + 1;
    }

    public ChatMessage create(String userId,
                              String author,
                              String type,
                              String body) {

        return new ChatMessage(
                getId(),
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
