package com.example.mindmines.data.entities.crossref;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import com.example.mindmines.data.entities.CharEntity;
import com.example.mindmines.data.entities.ExpeditionEntity;

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
