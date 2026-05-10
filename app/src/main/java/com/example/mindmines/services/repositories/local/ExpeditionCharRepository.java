package com.example.mindmines.services.repositories.local;

import android.content.Context;

import com.example.mindmines.db.MindMinesDatabase;
import com.example.mindmines.db.dao.ExpeditionCharCrossRefDao;
import com.example.mindmines.db.entities.crossref.ExpeditionCharCrossRef;

import java.util.List;
import java.util.Optional;

public class ExpeditionCharRepository {
    protected Context context;
    protected ExpeditionCharCrossRefDao dao;

    public void init(Context context) {
        this.context = context;
        MindMinesDatabase db = MindMinesDatabase.getInstance(context);
        this.dao = db.expeditionCharCrossRefDao();
        initArray();
    }

    public void initArray() {
    }

    public List<ExpeditionCharCrossRef> getAll() {
        return dao.getAll();
    }

    public void setAll(List<ExpeditionCharCrossRef> arr) {
        dao.deleteAll();
        dao.insertAll(arr);
    }

    public void add(ExpeditionCharCrossRef item) {
        dao.insert(item);
    }

    public void remove(ExpeditionCharCrossRef item) {
        dao.delete(item);
    }

    public ExpeditionCharCrossRef get(int charId, int expeditionId) {
        Optional<ExpeditionCharCrossRef> res = dao.getByCharId(charId).stream()
                .filter(it -> it.expeditionId == expeditionId).findFirst();
        return res.orElse(null);
    }

    public void update(ExpeditionCharCrossRef item) {
        dao.update(item);
    }
}
