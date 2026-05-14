package com.example.mindmines.models.game.equipment.types;

import com.example.mindmines.models.game.characters.CharStats;
import com.example.mindmines.models.game.equipment.EquipmentPath;
import com.example.mindmines.models.game.equipment.SlotType;
import com.example.mindmines.models.interfaces.RepositoryItem;

public class Equipment implements RepositoryItem<Integer> {
    protected Integer equipId;
    protected String userId;
    protected String image;
    protected Integer level;
    protected Integer price;
    protected CharStats equipStats;
    protected SlotType slotType;

    protected EquipmentPath path;

    public Equipment() {
        this.level = 1;
        this.price = 0;
    }

    public Equipment(String image) {
        super();
        this.image = image;
    }

    public Equipment(Integer equipId,
                     String userId,
                     String image,
                     Integer level,
                     Integer price,
                     CharStats equipStats,
                     SlotType slotType,
                     EquipmentPath path
    ) {
        this.equipId = equipId;
        this.userId = userId;
        this.image = image;
        this.level = level;
        this.price = price;
        this.equipStats = equipStats;
        this.slotType = slotType;
        this.path = path;
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

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
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

    public EquipmentPath getPath() { return path; }
}
