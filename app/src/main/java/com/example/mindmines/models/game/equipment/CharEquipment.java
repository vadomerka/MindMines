package com.example.mindmines.models.game.equipment;

import com.example.mindmines.models.game.equipment.types.Equipment;

public class CharEquipment {
    private Equipment leftHand;
    private Equipment rightHand;
    private Equipment body;
    private Equipment legs;

    public CharEquipment() {}

    public Equipment getLeftHand() {
        return leftHand;
    }
    public void setLeftHand(Equipment leftHand) {
        this.leftHand = leftHand;
    }
    public Equipment getRightHand() {
        return rightHand;
    }

    public void setRightHand(Equipment rightHand) {
        this.rightHand = rightHand;
    }

    public Equipment getBody() {
        return body;
    }

    public void setBody(Equipment body) {
        this.body = body;
    }
    public Equipment getLegs() {
        return legs;
    }
    public void setLegs(Equipment legs) {
        this.legs = legs;
    }
}
