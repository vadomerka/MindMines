package com.example.mindmines.services.factories;

import com.example.mindmines.services.repositories.DBEntity;
import com.example.mindmines.services.repositories.RepositoryItem;

public interface RepFactory <TId, T extends RepositoryItem<TId>, TEntity extends DBEntity>{
    T toItem(TEntity entity);
    TEntity toEntity(T item);
}
