package com.example.mindmines.services.observers;

import com.example.mindmines.models.game.expeditions.Expedition;

import java.util.List;

public interface ExpeditionObserver extends RepositoryObserver<Expedition> {
    void update(List<Expedition> upd);
}
