package com.example.mindmines.db.entities;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.OffsetDateTime;

@Entity(tableName = "habits")
public class HabitEntity {
    @PrimaryKey
    public Integer habitId;
    @ColumnInfo(name = "userId")
    public Integer userId;
    @ColumnInfo(name = "type")
    public String type;

    @ColumnInfo(name = "title")
    public String title;
    @ColumnInfo(name = "description")
    public String description;
    @ColumnInfo(name = "priority")
    public Integer priority;
    @ColumnInfo(name = "difficulty")
    public Integer difficulty;
    @ColumnInfo(name = "penaltyNumber")
    public Integer penaltyNumber;
    @ColumnInfo(name = "streakNumber")
    public Integer streakNumber;

    @ColumnInfo(name = "creationDate")
    public OffsetDateTime creationDate;
    @ColumnInfo(name = "lastCompletedAt")
    public OffsetDateTime lastCompletedAt;
    @ColumnInfo(name = "nextDeadlineAt")
    public OffsetDateTime nextDeadlineAt;
    @ColumnInfo(name = "intervalNumber")
    public Integer intervalNumber;
    @ColumnInfo(name = "intervalUnit")
    public String intervalUnit;

    public HabitEntity() {
    }

    public HabitEntity(
            Integer habitId,
            Integer userId,
            String type,
            String title,
            String description,
            Integer priority,
            Integer difficulty,
            Integer penaltyNumber,
            Integer streakNumber,
            OffsetDateTime creationDate,
            OffsetDateTime lastCompletedAt,
            OffsetDateTime nextDeadlineAt,
            Integer intervalNumber,
            String intervalUnit
    ) {
        this.habitId = habitId;
        this.userId = userId;
        this.type = type;

        this.title = title;
        this.description = description;
        this.priority = priority;
        this.difficulty = difficulty;
        this.penaltyNumber = penaltyNumber;
        this.streakNumber = streakNumber;

        this.creationDate = creationDate;
        this.lastCompletedAt = lastCompletedAt;
        this.nextDeadlineAt = nextDeadlineAt;
        this.intervalNumber = intervalNumber;
        this.intervalUnit = intervalUnit;
    }
}
