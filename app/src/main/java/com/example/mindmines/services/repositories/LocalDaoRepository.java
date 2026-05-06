package com.example.mindmines.services.repositories;

import android.content.Context;

import com.example.mindmines.db.dao.RepDao;
import com.example.mindmines.services.factories.RepFactory;
import com.example.mindmines.services.observers.RepositoryObserver;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class LocalDaoRepository<TId,
                                         T extends RepositoryItem<TId>,
                                         TEntity extends DBEntity,
                                         TObserver extends RepositoryObserver<T>>
            extends LocalObservedRepository<TId, T, TObserver> {

    protected RepDao<TEntity> dao;

    protected RepFactory<TId, T, TEntity> factory;

    public void init(Context context) {
        initFactory();
        initArray();
        this.context = context;
    }

    public abstract void initFactory();

    public void initArray() {}

    protected List<TEntity> toEntityList(List<T> items) {
        return items.stream().map(e -> factory.toEntity(e)).collect(Collectors.toList());
    }

    protected List<T> toItemList(List<TEntity> items) {
        return items.stream().map(e -> factory.toItem(e)).collect(Collectors.toList());
    }

    public List<T> getAll() {
        return toItemList(dao.getAll());
    }

    @Override
    public List<T> getByUser(String userId) {
        return toItemList(dao.getAllByUserId(userId));
    }

    @Override
    public void setAll(String userId, List<T> arr) {
        dao.deleteAllByUserId(userId);
        dao.insertAll(toEntityList(arr));
        super.setAll(userId, arr);
    }

    @Override
    public void add(T item) {
        dao.insert(factory.toEntity(item));
        super.add(item);
    }

    @Override
    public void remove(T item) {
        dao.delete(factory.toEntity(item));
        super.remove(item);
    }

    @Override
    public T get(TId id) {
        Optional<T> res = getAll().stream().filter(item -> item.getId().equals(id)).findFirst();
        return res.orElse(null);
    }

    public void update(T item) {
        dao.update(factory.toEntity(item));
        super.update(item);
    }
}
