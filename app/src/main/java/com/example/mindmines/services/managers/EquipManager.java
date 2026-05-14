package com.example.mindmines.services.managers;

import android.content.Context;

import com.example.mindmines.models.game.equipment.SlotType;
import com.example.mindmines.models.game.equipment.types.Equipment;
import com.example.mindmines.services.auth.AuthManager;
import com.example.mindmines.services.factories.EquipFactory;
import com.example.mindmines.services.repositories.RepositoryService;
import com.example.mindmines.services.repositories.dao.EquipRepository;

public class EquipManager {
    private static EquipManager instance;
    private final EquipRepository rep;
    private final Context context;

    public EquipManager(Context context) {
        this.context = context;
        rep = RepositoryService.getEquipRepository();
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

    public Equipment upgrade(Equipment eq) {
        int nLevel = eq.getLevel() + 1;
        eq.setLevel(nLevel);
        eq.getEquipStats().mult(
                1f + nLevel / 10f,
                1f + nLevel / 10f,
                1f + nLevel / 10f);
        eq.setPrice(eq.getPrice() + 1);
        return eq;
    }
}
