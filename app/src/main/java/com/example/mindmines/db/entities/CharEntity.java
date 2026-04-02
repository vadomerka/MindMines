package com.example.mindmines.db.entities;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "characters")
public class CharEntity {
    @PrimaryKey
    @NotNull
    public Integer charId;
    @ColumnInfo(name = "charJson")
    public String charJson;

    public CharEntity() {
        charId = 1;
    }

    public CharEntity(
            Integer charId,
            String charJson
    ) {
        this.charId = charId;
        this.charJson = charJson;
    }
}
