package com.example.mindmines.db.entities;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import java.time.OffsetDateTime;

@Entity(tableName = "expeditions")
public class ExpeditionEntity {
    @PrimaryKey
    @ColumnInfo(name = "expeditionId")
    @NotNull
    public Integer expeditionId;

    @ColumnInfo(name = "userId")
    public String userId;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "type")
    public String type;

    @ColumnInfo(name = "level")
    public Integer level;

    @ColumnInfo(name = "start")
    public OffsetDateTime start;

    @ColumnInfo(name = "finish")
    public OffsetDateTime finish;

    @ColumnInfo(name = "isFinished")
    public boolean isFinished;

    public ExpeditionEntity() {
        expeditionId = 1;
    }

    public ExpeditionEntity(
            @NonNull Integer expeditionId,
            String userId,
            String title,
            String type,
            Integer level,
            OffsetDateTime start,
            OffsetDateTime finish,
            boolean isFinished
    ) {
        this.expeditionId = expeditionId;
        this.userId = userId;
        this.title = title;
        this.type = type;
        this.level = level;
        this.start = start;
        this.finish = finish;
        this.isFinished = isFinished;
    }
}
