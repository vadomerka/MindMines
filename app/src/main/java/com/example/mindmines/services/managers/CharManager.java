package com.example.mindmines.services.managers;

import android.content.Context;

import com.example.mindmines.models.game.characters.Char;
import com.example.mindmines.models.game.characters.CharStatus;
import com.example.mindmines.models.game.equipment.types.Equipment;
import com.example.mindmines.models.game.expeditions.Expedition;
import com.example.mindmines.models.user.UserStatus;
import com.example.mindmines.services.auth.AuthManager;
import com.example.mindmines.services.factories.CharFactory;
import com.example.mindmines.services.observers.ExpeditionObserver;
import com.example.mindmines.services.repositories.RepositoryService;
import com.example.mindmines.services.repositories.dao.CharRepository;

public class CharManager {
    private static CharManager instance;
    private final CharRepository rep;
    private final CharFactory factory;
    private final ExpeditionObserver exProxy;
    private final Context context;

    private CharManager(Context context) {
        String userId = new AuthManager(context).getUserId();
        factory = CharFactory.getInstance();

        this.context = context;
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

    public void gain(Expedition ex) {
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
