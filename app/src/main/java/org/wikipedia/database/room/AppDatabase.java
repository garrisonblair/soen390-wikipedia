package org.wikipedia.database.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import org.wikipedia.notebook.database.NoteCommentsDao;
import org.wikipedia.notebook.database.NoteCommentsEntity;
import org.wikipedia.notebook.database.NoteDao;
import org.wikipedia.notebook.database.NoteEntity;
import org.wikipedia.notebook.database.ReferenceDao;
import org.wikipedia.notebook.database.ReferenceEntity;

/**
 * Created by Andres on 2018-03-08.
 */

@Database(entities = {NoteEntity.class, ReferenceEntity.class, NoteCommentsEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase{
    private static AppDatabase INSTANCE;
    public abstract NoteDao noteDao();
    public abstract ReferenceDao referenceDao();
    public abstract NoteCommentsDao noteCommentsDao();

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "SOEN390-database").allowMainThreadQueries().build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
