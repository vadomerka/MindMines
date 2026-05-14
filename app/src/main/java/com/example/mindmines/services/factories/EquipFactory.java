package com.example.mindmines.services.factories;

import com.example.mindmines.R;
import com.example.mindmines.models.game.characters.CharStats;
import com.example.mindmines.models.game.equipment.EquipmentPath;
import com.example.mindmines.models.game.equipment.SlotType;
import com.example.mindmines.models.game.equipment.types.Equipment;
import com.example.mindmines.services.repositories.RepositoryService;
import com.example.mindmines.services.repositories.dao.EquipRepository;

public class EquipFactory {
    private final EquipRepository rep;
    private static EquipFactory instance;

    public EquipFactory() {
        this.rep = RepositoryService.getEquipRepository();
    }

    public static EquipFactory getInstance() {
        if (instance == null) {
            instance = new EquipFactory();
        }
        return instance;
    }

    public Equipment generate(String userId, SlotType type) {
        int image;
        switch (type) {
            case LEFT_HAND:
                image = R.drawable.eq1_left_hand;
                break;
            case RIGHT_HAND:
                image = R.drawable.eq2_right_hand;
                break;
            case BODY_ARMOR:
                image = R.drawable.eq3_body;
                break;
            default:
                image = R.drawable.eq4_legs;
                break;
        }
        return new Equipment(
                rep.getId(),
                userId,
                String.valueOf(image),
                0,
                0,
                new CharStats(0, 0, 0),
                type,
                EquipmentPath.EMPTY
        );
    }

    public Equipment copyEquipment(Equipment eq) {
        CharStats sourceStats = eq.getEquipStats();
        return new Equipment(
                eq.getId(),
                eq.getUserId(),
                eq.getImage(),
                eq.getLevel(),
                eq.getPrice(),
                new CharStats(sourceStats.getAttack(), sourceStats.getDefence(), sourceStats.getSpeed()),
                eq.getSlotType(),
                eq.getPath()
        );
    }
}
