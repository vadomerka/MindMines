package com.example.mindmines.services.repositories;

import com.example.mindmines.R;
import com.example.mindmines.models.game.Expedition;
import com.example.mindmines.services.factories.ExpeditionFactory;
import com.example.mindmines.views.observers.ExpeditionObserver;

import java.util.ArrayList;
import java.util.Optional;

public class ExpeditionRepository extends LocalRepository<Expedition, ExpeditionObserver> {
    @Override
    public void initArray() {
        array = new ArrayList<Expedition>() {
            {
                add(ExpeditionFactory.generate(1, String.valueOf(R.drawable.expedition_1)));
                add(ExpeditionFactory.generate(2, String.valueOf(R.drawable.expedition_1)));
                add(ExpeditionFactory.generate(3, String.valueOf(R.drawable.expedition_1)));
            }
        };
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
