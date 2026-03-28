package com.example.mindmines.models.game.equipment.types;

import com.example.mindmines.models.game.CharStats;
import com.example.mindmines.models.game.equipment.SlotType;

public abstract class Equipment {
    protected String type;
    protected Integer level;
    protected CharStats equipStats;
    protected SlotType slotType;

    public String getType() { return type; }
    public Integer getLevel() { return level; }
    public void setLevel(Integer level) { this.level = level; }
    public CharStats getEquipStats() { return equipStats; }
    public void setEquipStats(CharStats equipStats) { this.equipStats = equipStats; }
    public SlotType getSlotType() {return slotType;}
}
