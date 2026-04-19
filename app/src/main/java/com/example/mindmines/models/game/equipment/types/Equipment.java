package com.example.mindmines.models.game.equipment.types;

import com.example.mindmines.models.game.characters.CharStats;
import com.example.mindmines.models.game.equipment.SlotType;

public class Equipment {
    protected String image;
    protected Integer level;
    protected CharStats equipStats;
    protected SlotType slotType;

    public Equipment() {
        this.level = 0;
    }

    public String getImage() { return image; }
    public Integer getLevel() { return level; }
    public void setLevel(Integer level) { this.level = level; }
    public CharStats getEquipStats() { return equipStats; }
    public void setEquipStats(CharStats equipStats) { this.equipStats = equipStats; }
    public SlotType getSlotType() {return slotType;}
}
