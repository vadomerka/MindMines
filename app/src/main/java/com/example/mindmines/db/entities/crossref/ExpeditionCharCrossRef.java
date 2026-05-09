package com.example.mindmines.db.entities.crossref;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

import com.example.mindmines.db.entities.CharEntity;
import com.example.mindmines.db.entities.ExpeditionEntity;

@Entity(
        tableName = "expedition_char_cross_ref",
        primaryKeys = {"expeditionId", "charId"},
        foreignKeys = {
                @ForeignKey(
                        entity = ExpeditionEntity.class,
                        parentColumns = "expeditionId",
                        childColumns = "expeditionId",
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = CharEntity.class,
                        parentColumns = "charId",
                        childColumns = "charId",
                        onDelete = ForeignKey.CASCADE
                )
        },
        indices = {@Index("charId"), @Index("expeditionId")}
)
public class ExpeditionCharCrossRef {
    public int expeditionId;
    public int charId;

    public ExpeditionCharCrossRef(int expeditionId, int charId) {
        this.expeditionId = expeditionId;
        this.charId = charId;
    }
}
