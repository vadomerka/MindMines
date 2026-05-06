package com.example.mindmines.db.dao;


import java.util.List;

public interface RepDao <TEntity> {
    List<TEntity> getAll();

    List<TEntity> getAllByUserId(String userId);

    void insertAll(List<TEntity> entities);

    void insert(TEntity entity);

    void update(TEntity entity);

    void deleteAll();

    void delete(TEntity entity);

    void deleteAllByUserId(String userId);
}
