package com.example.mindmines.models.game.equipment.types;

import com.example.mindmines.models.game.characters.CharStats;
import com.example.mindmines.models.game.equipment.EquipmentPath;
import com.example.mindmines.models.game.equipment.SlotType;

public class Sword extends Equipment {
    public Sword(String image) {
        this.image = image;
        this.equipStats = new CharStats(10, 0, 0);
        this.slotType = SlotType.RIGHT_HAND;
        path = EquipmentPath.SWORD;
    }
}
