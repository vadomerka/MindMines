package com.example.mindmines.models.game.equipment.types;

import com.example.mindmines.models.game.characters.CharStats;
import com.example.mindmines.models.game.equipment.EquipmentPath;
import com.example.mindmines.models.game.equipment.SlotType;

public class BodyArmor extends Equipment {
    public BodyArmor(int level, String image) {
        this.image = image;
        this.level = level;
        this.equipStats = getByLevel(level);
        this.slotType = SlotType.BODY_ARMOR;
        path = EquipmentPath.BODY_ARMOR;
    }

    protected CharStats getByLevel(int level) {
        baseEquipStats = new CharStats(0, 40, -5);
        for (int i = 1; i < level; i++) {
            baseEquipStats.mult(1, 1.25f, 1.1f);
        }
        return baseEquipStats;
    }
}
