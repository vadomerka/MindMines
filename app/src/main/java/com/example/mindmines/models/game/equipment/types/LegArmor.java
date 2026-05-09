package com.example.mindmines.models.game.equipment.types;

import com.example.mindmines.R;
import com.example.mindmines.models.game.characters.CharStats;
import com.example.mindmines.models.game.equipment.SlotType;

public class LegArmor extends Equipment {
    public LegArmor() {
        image = String.valueOf(R.drawable.equip_boots);
        equipStats = new CharStats(0, 10, 20);
        slotType = SlotType.LEGS_ARMOR;
    }
}
