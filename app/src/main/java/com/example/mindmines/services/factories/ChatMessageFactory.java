package com.example.mindmines.services.factories;

import com.example.mindmines.models.chat.ChatMessage;
import com.example.mindmines.services.repositories.ChatMessageRepository;
import com.example.mindmines.services.repositories.RepositoryService;

import java.time.OffsetDateTime;
import java.util.OptionalInt;

public class ChatMessageFactory {
    private static int getId() {
        ChatMessageRepository rep = RepositoryService.getChatMessageRepository();
        OptionalInt rm = rep.getAll() != null
                ? rep.getAll().stream().mapToInt(ChatMessage::getId).max()
                : OptionalInt.of(0);
        return (rm.isPresent() ? rm.getAsInt() : 0) + 1;
    }

    public static ChatMessage create(String author,
                                     String type,
                                     String body) {

        return new ChatMessage(
                getId(),
                author,
                type,
                "",
                body,
                OffsetDateTime.now(),
                null
        );
    }
}
