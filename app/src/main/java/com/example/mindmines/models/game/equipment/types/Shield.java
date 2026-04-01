package com.example.mindmines.models.game.equipment.types;

import com.example.mindmines.R;
import com.example.mindmines.models.game.CharStats;
import com.example.mindmines.models.game.equipment.SlotType;

public class Shield extends Equipment {
    public Shield() {
        image = String.valueOf(R.drawable.equip_holy_shield);
        equipStats = new CharStats(0, 20, 0);
        slotType = SlotType.LEFT_HAND;
    }
}
