package com.example.mindmines.models.game.equipment.types;

import com.example.mindmines.models.game.CharStats;
import com.example.mindmines.models.game.equipment.SlotType;

public class BodyArmor extends Equipment {
    public BodyArmor() {
        type = "bodyArmor";
        equipStats = new CharStats(0, 40, -5);
        slotType = SlotType.BODY_ARMOR;
    }
}
