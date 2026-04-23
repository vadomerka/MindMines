package com.example.mindmines.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.mindmines.db.dao.CharDao;
import com.example.mindmines.db.dao.ExpeditionCharCrossRefDao;
import com.example.mindmines.db.dao.ExpeditionDao;
import com.example.mindmines.db.dao.HabitDao;
import com.example.mindmines.db.entities.CharEntity;
import com.example.mindmines.db.entities.ExpeditionEntity;
import com.example.mindmines.db.entities.HabitEntity;
import com.example.mindmines.db.entities.crossref.ExpeditionCharCrossRef;

import java.time.OffsetDateTime;

@Database(entities = {HabitEntity.class, CharEntity.class, ExpeditionEntity.class,
        ExpeditionCharCrossRef.class}, version = 5, exportSchema = false)
@TypeConverters(HabitTypeConverter.class)
public abstract class MindMinesDatabase extends RoomDatabase {
    private static volatile MindMinesDatabase INSTANCE;

    public abstract HabitDao habitDao();

    public abstract CharDao charDao();

    public abstract ExpeditionDao expeditionDao();
    public abstract ExpeditionCharCrossRefDao expeditionCharCrossRefDao();

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
                            .addMigrations(MIGRATION_1_2)
                            .addMigrations(MIGRATION_2_3)
                            .addMigrations(MIGRATION_3_4)
                            .addMigrations(MIGRATION_4_5)
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

    static final Migration MIGRATION_3_4 = new Migration(3, 4) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE IF NOT EXISTS `expeditions` (`expeditionId` INTEGER NOT NULL, " +
                    "`title` TEXT, `type` TEXT, `level` INTEGER, `start` TEXT, `finish` TEXT, `isFinished` INTEGER, " +
                    "PRIMARY KEY(`expeditionId`))");
        }
    };

    static final Migration MIGRATION_4_5 = new Migration(4, 5) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE IF NOT EXISTS `expedition_char_cross_ref` (" +
                    "`expeditionId` INTEGER NOT NULL, " +
                    "`charId` INTEGER NOT NULL, " +
                    "PRIMARY KEY(`expeditionId`, `charId`), " +
                    "FOREIGN KEY(`expeditionId`) REFERENCES `expeditions`(`expeditionId`) ON DELETE CASCADE, " +
                    "FOREIGN KEY(`charId`) REFERENCES `characters`(`charId`) ON DELETE CASCADE)");
        }
    };
}
