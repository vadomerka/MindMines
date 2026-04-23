package com.example.mindmines.services.repositories;

import com.example.mindmines.db.entities.crossref.ExpeditionCharCrossRef;

import java.util.List;
import java.util.Optional;

public class ExpeditionCharRepository {
    protected List<ExpeditionCharCrossRef> array;

    public void init() {
        initArray();
    }

    public void initArray() {}

    public List<ExpeditionCharCrossRef> getAll() {
        return array;
    }

    public void setAll(List<ExpeditionCharCrossRef> arr) {
        array = arr;
    }

    public void add(ExpeditionCharCrossRef item) {
        array.add(item);
    }

    public void remove(ExpeditionCharCrossRef item) {
        array.remove(item);
    }

    public ExpeditionCharCrossRef get(int charId, int expeditionId) {
        Optional<ExpeditionCharCrossRef> res = array.stream()
                .filter(item -> item.charId == charId && item.expeditionId == expeditionId)
                .findFirst();
        return res.orElse(null);
    }

    public void update(ExpeditionCharCrossRef item) {
        ExpeditionCharCrossRef found = get(item.charId, item.expeditionId);
        array.set(array.indexOf(found), item);
    }
}
