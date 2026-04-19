package com.example.mindmines.views.observers;

import com.example.mindmines.models.game.Expedition;

import java.util.List;

public interface ExpeditionObserver extends RepositoryObserver<Expedition> {
    void update(List<Expedition> upd);
}
