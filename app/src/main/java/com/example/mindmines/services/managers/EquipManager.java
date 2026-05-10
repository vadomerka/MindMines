package com.example.mindmines.services.managers;

import com.example.mindmines.models.game.characters.CharStats;
import com.example.mindmines.models.game.equipment.types.Equipment;
import com.example.mindmines.services.repositories.RepositoryService;
import com.example.mindmines.services.repositories.dao.EquipRepository;

public class EquipManager {
    private static EquipManager instance;
    private final EquipRepository rep;

    public EquipManager() {
        rep = RepositoryService.getEquipRepository();
    }

    public static EquipManager getInstance() {
        if (instance == null) {
            instance = new EquipManager();
        }
        return instance;
    }

    public Equipment upgrade(Equipment eq) {
        int nLevel = eq.getLevel() + 1;
        eq.setLevel(nLevel);
        eq.getEquipStats().mult(new CharStats(nLevel, nLevel, nLevel));
        return eq;
    }
}
