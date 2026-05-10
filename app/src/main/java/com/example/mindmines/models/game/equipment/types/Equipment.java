package com.example.mindmines.models.game.equipment.types;

import com.example.mindmines.models.game.characters.CharStats;
import com.example.mindmines.models.game.equipment.SlotType;
import com.example.mindmines.models.interfaces.RepositoryItem;

public class Equipment implements RepositoryItem<Integer> {
    protected Integer equipId;
    protected String userId;
    protected String image;
    protected Integer level;
    protected CharStats equipStats;
    protected SlotType slotType;

    public Equipment() {
        this.level = 0;
    }

    public Equipment(Integer equipId,
                     String userId,
                     String image,
                     Integer level,
                     CharStats equipStats,
                     SlotType slotType
    ) {
        this.equipId = equipId;
        this.userId = userId;
        this.image = image;
        this.level = level;
        this.equipStats = equipStats;
        this.slotType = slotType;
    }


    public Integer getId() {
        return equipId;
    }

    public String getUserId() {
        return userId;
    }

    public String getImage() {
        return image;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public CharStats getEquipStats() {
        return equipStats;
    }

    public void setEquipStats(CharStats equipStats) {
        this.equipStats = equipStats;
    }

    public SlotType getSlotType() {
        return slotType;
    }

}
