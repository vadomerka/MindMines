package com.example.mindmines.services.repositories;

import com.example.mindmines.models.game.expeditions.Expedition;
import com.example.mindmines.views.observers.ExpeditionObserver;

import java.util.ArrayList;
import java.util.Optional;

public class ExpeditionRepository extends LocalObservedRepository<Expedition, ExpeditionObserver> {
    @Override
    public void initArray() {
        array = new ArrayList<>();
    }

    @Override
    public Expedition get(Object obj) {
        int itemId = (int) obj;
        Optional<Expedition> res = array.stream().filter(i -> i.getExpeditionId() == itemId).findFirst();
        return res.orElse(null);
    }

    @Override
    public void update(Expedition item) {
        Expedition found = get(item.getExpeditionId());
        array.set(array.indexOf(found), item);
        updateObservers();
    }
}
