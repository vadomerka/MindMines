package com.example.mindmines.models.game.equipment.types;

import com.example.mindmines.models.game.characters.CharStats;
import com.example.mindmines.models.game.equipment.EquipmentPath;
import com.example.mindmines.models.game.equipment.SlotType;

public class LegArmor extends Equipment {

    public LegArmor(int level, String image) {
        this.image = image;
        this.level = level;
        this.equipStats = getByLevel(level);
        this.slotType = SlotType.LEGS_ARMOR;
        path = EquipmentPath.LEG_ARMOR;
    }

    protected CharStats getByLevel(int level) {
        baseEquipStats = new CharStats(0, 10, 20);
        for (int i = 1; i < level; i++) {
            baseEquipStats.mult(1, 1.1f, 1.25f);
        }
        return baseEquipStats;
    }
}
