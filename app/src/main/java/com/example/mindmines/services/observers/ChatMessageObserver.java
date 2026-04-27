package com.example.mindmines.services.observers;

import com.example.mindmines.models.chat.ChatMessage;
import com.example.mindmines.models.game.characters.Char;

import java.util.List;

public interface ChatMessageObserver extends RepositoryObserver<ChatMessage> {
    void update(List<ChatMessage> upd);
}
