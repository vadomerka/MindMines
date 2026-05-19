package com.example.mindmines.models.game.equipment.types;

import com.example.mindmines.models.game.characters.CharStats;
import com.example.mindmines.models.game.equipment.EquipmentPath;
import com.example.mindmines.models.game.equipment.SlotType;

public class Staff extends Equipment {
    public Staff(int level, String image) {
        this.image = image;
        this.level = level;
        this.equipStats = getByLevel(level);
        this.slotType = SlotType.RIGHT_HAND;
        path = EquipmentPath.STAFF;
    }

    protected CharStats getByLevel(int level) {
        baseEquipStats = new CharStats(20, -10, -10);
        for (int i = 1; i < level; i++) {
            baseEquipStats.mult(1.5f, 1.1f, 1f);
        }
        return baseEquipStats;
    }
}
