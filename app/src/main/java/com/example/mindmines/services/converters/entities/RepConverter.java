package com.example.mindmines.services.converters.entities;

import com.example.mindmines.models.interfaces.DBEntity;
import com.example.mindmines.models.interfaces.RepositoryItem;

public interface RepConverter<TId, T extends RepositoryItem<TId>, TEntity extends DBEntity>{
    T toItem(TEntity entity);
    TEntity toEntity(T item);
}
