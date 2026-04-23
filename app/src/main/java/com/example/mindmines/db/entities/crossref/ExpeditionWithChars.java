package com.example.mindmines.db.entities.crossref;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import com.example.mindmines.db.entities.CharEntity;
import com.example.mindmines.db.entities.ExpeditionEntity;

import java.util.List;

public class ExpeditionWithChars {
    @Embedded
    public ExpeditionEntity expedition;

    @Relation(
            parentColumn = "expeditionId",
            entityColumn = "charId",
            associateBy = @Junction(
                    value = ExpeditionCharCrossRef.class,
                    parentColumn = "expeditionId",
                    entityColumn = "charId"
            )
    )
    public List<CharEntity> characters;
}
