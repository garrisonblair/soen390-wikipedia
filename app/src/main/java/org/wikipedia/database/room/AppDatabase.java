package org.wikipedia.database.room;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import org.wikipedia.notebook.database.NoteDao;
import org.wikipedia.notebook.database.NoteEntity;
import org.wikipedia.notebook.database.ReferenceDao;
import org.wikipedia.notebook.database.ReferenceEntity;
import org.wikipedia.userstatistics.Database.AchievementDao;
import org.wikipedia.userstatistics.Database.AchievementEntity;

import java.util.concurrent.Executors;

/**
 * Created by Andres on 2018-03-08.
 */

@Database(entities = {NoteEntity.class, ReferenceEntity.class, AchievementEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase{
    private static AppDatabase INSTANCE;
    public abstract NoteDao noteDao();
    public abstract ReferenceDao referenceDao();
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
                    .allowMainThreadQueries().build();

        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
