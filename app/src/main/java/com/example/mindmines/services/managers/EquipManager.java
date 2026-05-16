package com.example.mindmines.services.managers;

import android.content.Context;

import com.example.mindmines.models.game.characters.Char;
import com.example.mindmines.models.game.equipment.SlotType;
import com.example.mindmines.models.game.equipment.types.Equipment;
import com.example.mindmines.services.auth.AuthManager;
import com.example.mindmines.services.factories.EquipFactory;
import com.example.mindmines.services.repositories.RepositoryService;
import com.example.mindmines.services.repositories.dao.EquipRepository;

import java.util.Collections;
import java.util.List;

public class EquipManager {
    private static EquipManager instance;
    private final EquipRepository rep;
    private final PathwayManager pm;
    private final UserStatusManager usm;
    private final Context context;

    public EquipManager(Context context) {
        this.context = context;
        rep = RepositoryService.getEquipRepository();
        pm = PathwayManager.getInstance();
        usm = UserStatusManager.getInstance(context);
    }

    public static EquipManager getInstance(Context context) {
        if (instance == null) {
            instance = new EquipManager(context);
        }
        return instance;
    }

    public Equipment getDefaultSlot(SlotType type) {
        String userId = new AuthManager(context).getUserId();
        return EquipFactory.getInstance().generate(userId, type);
    }

    public List<Equipment> getUpgrades(Equipment eq) {
        List<Equipment> paths = pm.getNextPaths(eq);
        if (paths == null || paths.size() != 1) return paths;

        Equipment neq = paths.get(0);
        int nLevel = eq.getLevel() + 1;
        neq.setLevel(nLevel);
        eq.getEquipStats().mult(
                1f + nLevel / 10f,
                1f + nLevel / 10f,
                1f + nLevel / 10f);
        neq.setEquipStats(eq.getEquipStats());
        neq.setPrice(eq.getPrice() + 1);
        return Collections.singletonList(neq);
    }

    public void buyEquipment(Char ch, Equipment upgradedEq, SlotType type) {
        if (usm.cantBuyEquipment(upgradedEq)) return;
        usm.buyEquipment(upgradedEq);
        ch.unEquip(type);
        ch.equip(upgradedEq);
        RepositoryService.getCharRepository().update(ch);
    }
}
