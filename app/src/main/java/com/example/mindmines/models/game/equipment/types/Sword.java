package com.example.mindmines.models.game.equipment.types;

import com.example.mindmines.models.game.characters.CharStats;
import com.example.mindmines.models.game.equipment.EquipmentPath;
import com.example.mindmines.models.game.equipment.SlotType;

public class Sword extends Equipment {
    public Sword(int level, String image) {
        this.image = image;
        this.level = level;
        this.equipStats = getByLevel(level);
        this.slotType = SlotType.RIGHT_HAND;
        path = EquipmentPath.SWORD;
    }

    protected CharStats getByLevel(int level) {
        baseEquipStats = new CharStats(10, 0, 0);
        for (int i = 1; i < level; i++) {
            baseEquipStats.mult(1.25f, 1, 1);
        }
        return baseEquipStats;
    }
}
