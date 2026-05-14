package com.example.mindmines.models.game.equipment.types;

import com.example.mindmines.models.game.characters.CharStats;
import com.example.mindmines.models.game.equipment.EquipmentPath;
import com.example.mindmines.models.game.equipment.SlotType;

public class BodyArmor extends Equipment {
    public BodyArmor(String image) {
        this.image = image;
        equipStats = new CharStats(0, 40, -5);
        slotType = SlotType.BODY_ARMOR;
        path = EquipmentPath.BODY_ARMOR;
    }
}
