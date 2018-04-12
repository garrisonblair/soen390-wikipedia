package org.wikipedia.database.room;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.support.annotation.NonNull;

import org.wikipedia.notebook.database.NoteDao;
import org.wikipedia.notebook.database.NoteEntity;
import org.wikipedia.notebook.database.ReferenceDao;
import org.wikipedia.notebook.database.ReferenceEntity;

import org.wikipedia.userstatistics.Database.ArticleVisitDao;
import org.wikipedia.userstatistics.Database.ArticleVisitEntity;

import org.wikipedia.userstatistics.Database.AchievementDao;
import org.wikipedia.userstatistics.Database.AchievementEntity;

import java.util.concurrent.Executors;

@Database(entities = {NoteEntity.class, ReferenceEntity.class, ArticleVisitEntity.class, AchievementEntity.class}, version = 3)

public abstract class AppDatabase extends RoomDatabase{
    private static AppDatabase INSTANCE;
    public abstract NoteDao noteDao();
    public abstract ReferenceDao referenceDao();

    public abstract ArticleVisitDao articleVisitDao();

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE notes "
                    + " ADD COLUMN comment STRING");
        }
    };

    static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE 'articleVisits' ('id' INTEGER, "
                    + "'articleTitle' STRING, 'timeSpentReading' LONG, 'timeStart' LONG)");
        }
    };

    public abstract AchievementDao achievementDao();

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "SOEN390-database")
                    .addCallback(new Callback() {
                                    @Override
                                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                        super.onCreate(db);
                                        Executors.newSingleThreadScheduledExecutor().execute(() -> getInstance(context).achievementDao().addAllAchievements(AchievementEntity.populateData()));
                                    }
                                })
                    .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                    .allowMainThreadQueries().build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
