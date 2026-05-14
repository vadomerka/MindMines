package com.example.mindmines.models.game.equipment.types;

import com.example.mindmines.models.game.characters.CharStats;
import com.example.mindmines.models.game.equipment.EquipmentPath;
import com.example.mindmines.models.game.equipment.SlotType;

public class Shield extends Equipment {
    public Shield(String image) {
        this.image = image;
        equipStats = new CharStats(0, 20, 0);
        slotType = SlotType.LEFT_HAND;
        path = EquipmentPath.SHIELD;
    }
}
