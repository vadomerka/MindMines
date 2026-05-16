package com.example.mindmines.services.managers;

import com.example.mindmines.R;
import com.example.mindmines.models.game.equipment.EquipmentPath;
import com.example.mindmines.models.game.equipment.SlotType;
import com.example.mindmines.models.game.equipment.types.BodyArmor;
import com.example.mindmines.models.game.equipment.types.Equipment;
import com.example.mindmines.models.game.equipment.types.LegArmor;
import com.example.mindmines.models.game.equipment.types.Shield;
import com.example.mindmines.models.game.equipment.types.Staff;
import com.example.mindmines.models.game.equipment.types.Sword;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class PathwayManager {
    private final Map<EquipmentPath, List<Equipment>> pathways;

    private static PathwayManager instance;

    public PathwayManager() {
        pathways = new HashMap<>();
        pathways.put(EquipmentPath.SWORD, Arrays.asList(
                new Sword(String.valueOf(R.drawable.eq_sword1)),
                new Sword(String.valueOf(R.drawable.eq_sword2)),
                new Sword(String.valueOf(R.drawable.eq_sword3))
        ));
        pathways.put(EquipmentPath.SHIELD, Arrays.asList(
                new Shield(String.valueOf(R.drawable.eq_shield1)),
                new Shield(String.valueOf(R.drawable.eq_shield2)),
                new Shield(String.valueOf(R.drawable.eq_shield3))
        ));
        pathways.put(EquipmentPath.BODY_ARMOR, Arrays.asList(
                new BodyArmor(String.valueOf(R.drawable.eq_armor1)),
                new BodyArmor(String.valueOf(R.drawable.eq_armor2)),
                new BodyArmor(String.valueOf(R.drawable.eq_armor3))
        ));
        pathways.put(EquipmentPath.LEG_ARMOR, Arrays.asList(
                new LegArmor(String.valueOf(R.drawable.eq_boots)),
                new LegArmor(String.valueOf(R.drawable.eq_boots)),
                new LegArmor(String.valueOf(R.drawable.eq_boots))
        ));
        pathways.put(EquipmentPath.STAFF, Arrays.asList(
                new Staff(String.valueOf(R.drawable.eq_staff1)),
                new Staff(String.valueOf(R.drawable.eq_staff2)),
                new Staff(String.valueOf(R.drawable.eq_staff3))
        ));
    }

    public static PathwayManager getInstance() {
        if (instance == null) {
            instance = new PathwayManager();
        }
        return instance;
    }

    public Equipment getEquip(EquipmentPath path, int ind) {
        if (pathways.get(path) == null) return null;
        return Objects.requireNonNull(pathways.get(path)).get(ind);
    }

    public Equipment getEquip(EquipmentPath path) {
        return getEquip(path, 0);
    }

    public List<Equipment> getPathway(Equipment eq) {
        EquipmentPath c = eq.getPath();
        return pathways.getOrDefault(c, null);
    }

    public List<Equipment> getNextPaths(Equipment eq) {
        if (eq == null) {
            return getPathways();
        }
        if (eq.getPath() == EquipmentPath.EMPTY) {
            return getPathways(eq.getSlotType());
        }
        List<Equipment> path = getPathway(eq);
        for (int i = 0; i < path.size() - 1; i++) {
            if (path.get(i).getImage().equals(eq.getImage())) {
                return Collections.singletonList(path.get(i + 1));
            }
        }
        return Collections.singletonList(path.get(path.size() - 1));
    }

    public List<Equipment> getPathways() {
        List<Equipment> res = new ArrayList<>();
        for (EquipmentPath key: pathways.keySet()) {
            if (Objects.requireNonNull(pathways.get(key)).isEmpty()) continue;
            res.add(Objects.requireNonNull(pathways.get(key)).get(0));
        }
        return res;
    }

    public List<Equipment> getPathways(SlotType slotType) {
        return getPathways().stream().filter(eq -> eq.getSlotType() == slotType).collect(Collectors.toList());
    }
}
