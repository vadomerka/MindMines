package com.example.mindmines.services.managers;

import android.content.Context;

import com.example.mindmines.models.game.characters.Char;
import com.example.mindmines.models.game.characters.CharStatus;
import com.example.mindmines.models.game.equipment.types.Equipment;
import com.example.mindmines.models.game.expeditions.Expedition;
import com.example.mindmines.models.user.UserStatus;
import com.example.mindmines.services.factories.CharFactory;
import com.example.mindmines.services.observers.ExpeditionObserver;
import com.example.mindmines.services.repositories.RepositoryService;
import com.example.mindmines.services.repositories.dao.CharRepository;

import java.util.List;

public class CharManager {
    private static CharManager instance;
    private final CharRepository rep;
    private final CharFactory factory;
    private final ExpeditionObserver exProxy;
    private final Context context;

    private CharManager(Context context) {
        this.context = context;

        factory = CharFactory.getInstance();
        rep = RepositoryService.getCharRepository();
        exProxy = upd -> {
            if (!upd.isEmpty()) gain(upd.get(0));
        };
        RepositoryService.getExpeditionRepository().subscribe(exProxy);
    }

    public static CharManager getInstance(Context context) {
        if (instance == null) {
            instance = new CharManager(context);
        }
        return instance;
    }

    public Integer getMinLevel() {
        List<Char> chars = rep.getByUser();
        if (chars == null || chars.isEmpty()) return 0;
        return chars.stream().map(it -> it.getStatus().getLevel()).min(Integer::compareTo).get();
    }

    public void gain(Expedition ex) {
        if (!ex.isFinished()) return;
        for (Char ch : rep.getByUser()) {
            long newExp = XpManager.expeditionToCharExp(ch, ex);
            gain(ch, newExp);
        }
    }

    public void gain(Char ch, Long xp) {
        CharStatus status = ch.getStatus();
        XpManager.gainLevel(status, xp);
        ch.setStatus(status);
        rep.update(ch);
    }

    public void unlockAvailableChars(UserStatus status) {
        if (rep.getByUser().size() == 0 && status.getLevel() >= 0)
            rep.add(factory.generateDefault(status.getUserId(), 1));
        if (rep.getByUser().size() == 1 && status.getLevel() >= 3)
            rep.add(factory.generateDefault(status.getUserId(), 2));
        if (rep.getByUser().size() == 2 && status.getLevel() >= 5)
            rep.add(factory.generateDefault(status.getUserId(), 3));
        if (rep.getByUser().size() == 3 && status.getLevel() >= 10)
            rep.add(factory.generateDefault(status.getUserId(), 4));
    }

    public boolean cantBuyEquipment(Char ch, Equipment upgradedEq) {
        return ch.getStatus().getLevel() < upgradedEq.getLevel();
    }
}
