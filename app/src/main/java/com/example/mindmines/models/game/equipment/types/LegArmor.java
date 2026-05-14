package com.example.mindmines.models.game.equipment.types;

import com.example.mindmines.models.game.characters.CharStats;
import com.example.mindmines.models.game.equipment.EquipmentPath;
import com.example.mindmines.models.game.equipment.SlotType;

public class LegArmor extends Equipment {
    public LegArmor(String image) {
        this.image = image;
        equipStats = new CharStats(0, 10, 20);
        slotType = SlotType.LEGS_ARMOR;
        path = EquipmentPath.LEG_ARMOR;
    }
}
