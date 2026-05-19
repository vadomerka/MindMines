package com.example.mindmines.models.game.equipment.types;

import com.example.mindmines.models.game.characters.CharStats;
import com.example.mindmines.models.game.equipment.EquipmentPath;
import com.example.mindmines.models.game.equipment.SlotType;

public class Shield extends Equipment {
    public Shield(int level, String image) {
        this.image = image;
        this.level = level;
        this.equipStats = getByLevel(level);
        this.slotType = SlotType.LEFT_HAND;
        path = EquipmentPath.SHIELD;
    }

    protected CharStats getByLevel(int level) {
        baseEquipStats = new CharStats(0, 20, 0);
        for (int i = 1; i < level; i++) {
            baseEquipStats.mult(1, 1.25f, 1);
        }
        return baseEquipStats;
    }
}
