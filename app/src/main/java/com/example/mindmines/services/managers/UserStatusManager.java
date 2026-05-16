package com.example.mindmines.services.managers;

import android.content.Context;

import com.example.mindmines.models.game.equipment.types.Equipment;
import com.example.mindmines.models.game.expeditions.Expedition;
import com.example.mindmines.models.habits.Habit;
import com.example.mindmines.models.user.UserStatus;
import com.example.mindmines.services.auth.AuthManager;
import com.example.mindmines.services.factories.UserStatusFactory;
import com.example.mindmines.services.observers.ExpeditionObserver;
import com.example.mindmines.services.observers.UserStatusObserver;
import com.example.mindmines.services.repositories.RepositoryService;
import com.example.mindmines.services.repositories.dao.UserStatusRepository;

public class UserStatusManager {
    private static UserStatusManager instance;
    private final UserStatusRepository rep;
    private final Context context;
    private final ExpeditionObserver exProxy;
    private final UserStatusObserver usProxy;

    private UserStatusManager(Context context) {
        this.context = context;
        rep = RepositoryService.getUserStatusRepository();
        usProxy = upd -> {
            if (!upd.isEmpty()) unlock(upd.get(0));
        };
        rep.subscribe(usProxy);

        exProxy = upd -> {
            if (!upd.isEmpty() && upd.get(0).isFinished()) gain(upd.get(0));
        };
        RepositoryService.getExpeditionRepository().subscribe(exProxy);

    }

    public static UserStatusManager getInstance(Context context) {
        if (instance == null) {
            instance = new UserStatusManager(context);
        }
        return instance;
    }

    public UserStatus getStatus() {
        String userId = new AuthManager(context).getUserId();
        UserStatus status = rep.get(userId);
        return status;
    }

    public void addStatus(UserStatus s) {
        rep.add(s);
    }

    public void updateStatus(UserStatus s) {
        rep.update(s);
    }

    public void removeStatus() {
        rep.remove(getStatus());
    }

    public void tryRemoveStatus(String userToken) {
        if (rep.get(userToken) == null) return;
        rep.remove(rep.get(userToken));
    }

    public void resetStatus() {
        String userId = new AuthManager(context).getUserId();
        rep.update(UserStatusFactory.getInstance().create(userId));
    }

    public void gain(Habit h) {
        UserStatus status = getStatus();
        Long newExp = XpManager.habitToExp(h);
        XpManager.gainLevel(status, newExp);
        updateStatus(status);
        CharManager.getInstance(context).unlockAvailableChars(status);
    }

    public void gain(Expedition ex) {
        if (!ex.isFinished()) return;
        long coins = XpManager.expeditionToRewards(ex);
        gainCoins(coins);
    }

    public void gainCoins(Long coins) {
        UserStatus status = getStatus();
        status.setCoins(status.getCoins() + coins);
        updateStatus(status);
    }

    public void buyEquipment(Equipment equipment) {
        UserStatus status = getStatus();
        if (cantBuyEquipment(equipment)) return;
        status.setCoins(status.getCoins() - equipment.getPrice());
        updateStatus(status);
    }

    public boolean cantBuyEquipment(Equipment equipment) {
        UserStatus status = getStatus();
        return status.getCoins() < equipment.getPrice();
    }

    public void unlock(UserStatus status) {
        CharManager.getInstance(context).unlockAvailableChars(status);
    }
}
