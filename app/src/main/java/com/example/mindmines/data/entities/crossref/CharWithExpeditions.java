package com.example.mindmines.data.entities.crossref;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import com.example.mindmines.data.entities.CharEntity;
import com.example.mindmines.data.entities.ExpeditionEntity;

import java.util.List;

public class CharWithExpeditions {
    @Embedded
    public CharEntity character;

    @Relation(
            parentColumn = "charId",
            entityColumn = "expeditionId",
            associateBy = @Junction(
                    value = ExpeditionCharCrossRef.class,
                    parentColumn = "charId",
                    entityColumn = "expeditionId"
            )
    )
    public List<ExpeditionEntity> expeditions;
}
