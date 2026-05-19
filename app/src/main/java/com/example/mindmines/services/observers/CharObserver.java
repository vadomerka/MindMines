package com.example.mindmines.services.observers;

import com.example.mindmines.models.game.characters.Char;

import java.util.List;

public interface CharObserver extends RepositoryObserver<Char> {
    void update(List<Char> upd);
}
