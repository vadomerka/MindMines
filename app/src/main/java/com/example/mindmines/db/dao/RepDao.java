package com.example.mindmines.db.dao;


import java.util.List;

public interface RepDao<TEntity> {
    default List<TEntity> getAll() {
        throw new UnsupportedOperationException();
    }

    default List<TEntity> getAllByUserId(String userId) {
        throw new UnsupportedOperationException();
    }

    default void insertAll(List<TEntity> entities) {
        throw new UnsupportedOperationException();
    }

    default void insert(TEntity entity) {
        throw new UnsupportedOperationException();
    }

    default void update(TEntity entity) {
        throw new UnsupportedOperationException();
    }

    default void deleteAll() {
        throw new UnsupportedOperationException();
    }

    default void delete(TEntity entity) {
        throw new UnsupportedOperationException();
    }

    default void deleteAllByUserId(String userId) {
        throw new UnsupportedOperationException();
    }
}
