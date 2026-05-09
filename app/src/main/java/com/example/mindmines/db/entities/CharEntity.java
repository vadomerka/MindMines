package com.example.mindmines.db.entities;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.mindmines.models.interfaces.DBEntity;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "characters")
public class CharEntity implements DBEntity {
    @PrimaryKey
    @NotNull
    public Integer charId;

    @ColumnInfo(name = "userId")
    public String userId;

    @ColumnInfo(name = "charJson")
    public String charJson;

    public CharEntity() {
        charId = 1;
    }

    public CharEntity(
            @NonNull Integer charId,
            String userId,
            String charJson
    ) {
        this.charId = charId;
        this.userId = userId;
        this.charJson = charJson;
    }
}
