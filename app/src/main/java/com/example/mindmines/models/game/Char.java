package com.example.mindmines.models.game;

import com.example.mindmines.models.game.equipment.CharEquipment;
import com.example.mindmines.models.game.equipment.SlotType;
import com.example.mindmines.models.game.equipment.types.Equipment;

public class Char {
    private final Integer charId;
    private String name;
    private CharStats stats;
    private CharStatus status;
    private CharEquipment equipment;

    public Char() {
        this.charId = 1;
        this.name = "";
        this.stats = new CharStats();
        this.status = new CharStatus();
        this.equipment = new CharEquipment();
    }

    public Char(Integer charId, String name, CharStats stats,
                CharStatus status, CharEquipment equipment) {
        this.charId = charId;
        this.name = name;
        this.stats = stats;
        this.status = status;
        this.equipment = equipment;
    }

    public Integer getCharId() {
        return charId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CharStats getStats() {
        return stats;
    }

    public void setStats(CharStats stats) {
        this.stats = stats;
    }

    public CharStatus getStatus() {
        return status;
    }

    public void setStatus(CharStatus status) {
        this.status = status;
    }

    public CharEquipment getEquipment() {
        return equipment;
    }

    public void setEquipment(CharEquipment equipment) {
        this.equipment = equipment;
    }

    public void equip(Equipment item) {
        stats.add(item.getEquipStats());
        SlotType slot = item.getSlotType();
        if (slot == SlotType.LEFT_HAND && equipment.getLeftHand() == null) {
            equipment.setLeftHand(item);
        } else if (slot == SlotType.RIGHT_HAND && equipment.getRightHand() == null) {
            equipment.setRightHand(item);
        } else if (slot == SlotType.BODY_ARMOR && equipment.getBody() == null) {
            equipment.setBody(item);
        } else if (slot == SlotType.LEGS_ARMOR && equipment.getLegs() == null) {
            equipment.setLegs(item);
        } else {
            stats.sub(item.getEquipStats());
        }
    }

    public void unEquip(SlotType slot) {
        CharStats subStats = new CharStats();
        if (slot == SlotType.LEFT_HAND && equipment.getLeftHand() != null) {
            subStats = equipment.getLeftHand().getEquipStats();
            equipment.setLeftHand(null);
        } else if (slot == SlotType.RIGHT_HAND && equipment.getRightHand() != null) {
            subStats = equipment.getRightHand().getEquipStats();
            equipment.setRightHand(null);
        } else if (slot == SlotType.BODY_ARMOR && equipment.getBody() != null) {
            subStats = equipment.getBody().getEquipStats();
            equipment.setBody(null);
        } else if (slot == SlotType.LEGS_ARMOR && equipment.getLegs() != null) {
            subStats = equipment.getLegs().getEquipStats();
            equipment.setLegs(null);
        }
        stats.sub(subStats);
    }
}
