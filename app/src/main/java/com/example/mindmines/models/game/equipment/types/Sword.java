package com.example.mindmines.models.game.equipment.types;

import com.example.mindmines.R;
import com.example.mindmines.models.game.CharStats;
import com.example.mindmines.models.game.equipment.SlotType;

public class Sword extends Equipment {
    public Sword() {
        image = String.valueOf(R.drawable.equip_sword);
        equipStats = new CharStats(10, 0, 0);
        slotType = SlotType.RIGHT_HAND;
    }
}
