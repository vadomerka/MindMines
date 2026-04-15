package com.example.mindmines.db;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.mindmines.db.dao.CharDao;
import com.example.mindmines.db.dao.HabitDao;
import com.example.mindmines.db.entities.CharEntity;
import com.example.mindmines.db.entities.HabitEntity;

@Database(entities = {HabitEntity.class, CharEntity.class}, version = 3, exportSchema = false)
@TypeConverters(HabitTypeConverter.class)
public abstract class MindMinesDatabase extends RoomDatabase {
    private static volatile MindMinesDatabase INSTANCE;

    public abstract HabitDao habitDao();
    public abstract CharDao charDao();

    public static MindMinesDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (MindMinesDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            MindMinesDatabase.class,
                            "habits.db"
                    )
                            .allowMainThreadQueries()  // not recommended
                            .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE IF NOT EXISTS `characters` (`charId` INTEGER NOT NULL, `charJson` TEXT, PRIMARY KEY(`charId`))");
        }
    };

    static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE habits ADD COLUMN goalCount INTEGER DEFAULT 0;");
        }
    };
}
