package com.example.mindmines.db.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.mindmines.models.game.equipment.SlotType;
import com.example.mindmines.models.interfaces.DBEntity;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "equipment")
public class EquipEntity implements DBEntity {
    @PrimaryKey
    @NotNull
    public Integer equipId;

    @ColumnInfo(name = "userId")
    public String userId;

    @ColumnInfo(name = "image")
    public String image;

    @ColumnInfo(name = "level")
    public Integer level;

    @ColumnInfo(name = "price")
    public Integer price;

    @ColumnInfo(name = "equipStats")
    public String equipStats;

    @ColumnInfo(name = "slotType")
    public SlotType slotType;


    public EquipEntity(@NonNull
                       Integer equipId,
                       String userId,
                       String image,
                       Integer level,
                       Integer price,
                       String equipStats,
                       SlotType slotType
    ) {
        this.equipId = equipId;
        this.userId = userId;
        this.image = image;
        this.level = level;
        this.price = price;
        this.equipStats = equipStats;
        this.slotType = slotType;
    }
}
