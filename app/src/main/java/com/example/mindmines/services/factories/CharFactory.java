package com.example.mindmines.services.factories;

import com.example.mindmines.R;
import com.example.mindmines.models.game.characters.Char;
import com.example.mindmines.models.game.characters.CharStats;
import com.example.mindmines.models.game.characters.CharStatus;
import com.example.mindmines.models.game.equipment.CharEquipment;
import com.example.mindmines.models.game.equipment.EquipmentPath;
import com.example.mindmines.models.game.equipment.types.Equipment;
import com.example.mindmines.services.managers.PathwayManager;
import com.example.mindmines.services.repositories.RepositoryService;
import com.example.mindmines.services.repositories.dao.CharRepository;

import java.util.Random;

public class CharFactory {
    private static final int variation = 5;
    private static final int baseValue = 10;
    private static CharFactory instance;
    private final Random rnd = new Random();
    private final CharRepository rep;
    private final PathwayManager pm;

    public CharFactory() {
        this.rep = RepositoryService.getCharRepository();
        this.pm = PathwayManager.getInstance();
    }

    public static CharFactory getInstance() {
        if (instance == null) {
            instance = new CharFactory();
        }
        return instance;
    }

    public Char generateDefault(String userId, int defaultInd) {
        switch (defaultInd) {
            case 2:
                return generate(userId, "Грустный Шахтер", 3, String.valueOf(R.drawable.g3),
                        new Equipment[]{pm.getEquip(EquipmentPath.SWORD, 0),
                                pm.getEquip(EquipmentPath.LEG_ARMOR)});
            case 3:
                return generate(userId, "Боец Ученый", 5, String.valueOf(R.drawable.g4),
                        new Equipment[]{});
            case 4:
                return generate(userId, "Мистический маг", 10, String.valueOf(R.drawable.g5),
                        new Equipment[]{});
            default:
                return generate(userId, "Бывалый Пират", 1, String.valueOf(R.drawable.g2),
                        new Equipment[]{pm.getEquip(EquipmentPath.SWORD, 0),
                                pm.getEquip(EquipmentPath.SHIELD, 0),
                                pm.getEquip(EquipmentPath.BODY_ARMOR, 0),
                                pm.getEquip(EquipmentPath.LEG_ARMOR, 0)});
        }
    }

    public Char generate(String userId, String name) {
        return generate(userId, name, 0, String.valueOf(R.drawable.g0));
    }

    public Char generate(String userId, String name, int level, String image, Equipment[] equipment) {
        Char ch = generate(userId, name, level, image);
        for (Equipment eq : equipment) {
            ch.equip(eq);
        }
        return ch;
    }

    public Char generate(String userId, String name, int level, String image) {
        int atk = rnd.nextInt(variation) + baseValue * level;
        int defence = rnd.nextInt(variation) + baseValue * level;
        int speed = rnd.nextInt(variation) + baseValue * level;
        int hp = rnd.nextInt(variation) + baseValue * level;
        long maxExperience = rnd.nextInt(2) + (long) baseValue * level;
        return new Char(rep.getId() + 1, userId, name,
                new CharStats(atk, defence, speed),
                new CharStatus(hp, level, maxExperience), new CharEquipment(), image);
    }
}
