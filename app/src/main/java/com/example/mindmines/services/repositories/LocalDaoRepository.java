package com.example.mindmines.services.repositories;

import android.content.Context;

import com.example.mindmines.data.dao.RepDao;
import com.example.mindmines.models.interfaces.DBEntity;
import com.example.mindmines.models.interfaces.RepositoryItem;
import com.example.mindmines.services.auth.AuthManager;
import com.example.mindmines.services.converters.entities.RepConverter;
import com.example.mindmines.services.observers.RepositoryObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class LocalDaoRepository<TId extends Comparable<TId>,
        T extends RepositoryItem<TId>,
        TEntity extends DBEntity,
        TObserver extends RepositoryObserver<T>>
        extends LocalObservedRepository<TId, T, TObserver> {

    protected RepDao<TEntity> dao;

    protected RepConverter<TId, T, TEntity> converter;

    @Override
    public void init(Context context) {
        this.context = context;
        initDao();
        initConverter();
        observers = new ArrayList<>();
        updateObservers();
        initArray();
        loadArray();
    }

    public abstract void initDao();

    public abstract void initConverter();

    protected void loadArray() {
        String userId = new AuthManager(context).getUserId();
        super.setAll(userId, getAll());
    }

    @Override
    public TId getId() {
        return getAll().stream().map(T::getId).max(Comparable::compareTo).orElse(defaultId());
    }

    protected List<TEntity> toEntityList(List<T> items) {
        return items.stream().map(e -> converter.toEntity(e)).collect(Collectors.toList());
    }

    protected List<T> toItemList(List<TEntity> items) {
        return items.stream().map(e -> converter.toItem(e)).collect(Collectors.toList());
    }

    public List<T> getAll() {
        return toItemList(dao.getAll());
    }

    @Override
    public List<T> getByUser() {
        String userId = new AuthManager(context).getUserId();
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
        dao.insert(converter.toEntity(item));
        super.add(item);
    }

    @Override
    public void remove(T item) {
        dao.delete(converter.toEntity(item));
        super.remove(item);
    }

    public T get(TId id) {
        Optional<T> res = getAll().stream().filter(item -> item.getId().equals(id)).findFirst();
        return res.orElse(null);
    }

    public void update(T item) {
        dao.update(converter.toEntity(item));
        super.update(item);
    }
}
