package com.example.mindmines.models.game.equipment;

import com.example.mindmines.models.game.equipment.types.Equipment;

import java.io.Serializable;

public class CharEquipment implements Serializable {
    private Equipment leftHand;
    private Equipment rightHand;
    private Equipment body;
    private Equipment legs;

    public CharEquipment() {
    }

    public CharEquipment(Equipment leftHand, Equipment rightHand, Equipment body, Equipment legs) {
        this.leftHand = leftHand;
        this.rightHand = rightHand;
        this.body = body;
        this.legs = legs;
    }

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

    public Equipment getBySlot(SlotType slotType) {
        switch (slotType) {
            case LEFT_HAND:
                return getLeftHand();
            case BODY_ARMOR:
                return getBody();
            case LEGS_ARMOR:
                return getLegs();
            default:
                return getRightHand();
        }
    }
}
