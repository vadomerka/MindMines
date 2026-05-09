package com.example.mindmines.db.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.mindmines.models.interfaces.DBEntity;

@Entity(tableName = "userStatuses")
public class UserStatusEntity implements DBEntity {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "userId")
    public String userId;

    @ColumnInfo(name = "level")
    public Integer level;

    @ColumnInfo(name = "experience")
    public Long experience;

    @ColumnInfo(name = "maxExperience")
    public Long maxExperience;

    @ColumnInfo(name = "coins")
    public Long coins;

    public UserStatusEntity(@NonNull String userId,
                            Integer level,
                            Long experience,
                            Long maxExperience,
                            Long coins) {
        this.userId = userId;
        this.level = level;
        this.experience = experience;
        this.maxExperience = maxExperience;
        this.coins = coins;
    }
}