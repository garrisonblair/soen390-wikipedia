package org.wikipedia.database.room;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.wikipedia.notebook.database.NoteDao;
import org.wikipedia.notebook.database.NoteEntity;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Andres on 2018-03-08.
 */

@RunWith(AndroidJUnit4.class)
public class NoteDaoTest {
    private NoteDao mNoteDao;
    private AppDatabase mDb;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getTargetContext();
        mDb = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        mNoteDao = mDb.noteDao();
    }

    @After
    public void closeDb() throws IOException {
        mDb.close();
    }

    @Test
    public void readAndWriteNote() throws Exception {
        NoteEntity noteEntity = new NoteEntity(123,"Title", "new note");

        mNoteDao.addNote(noteEntity);

        List<NoteEntity> allNotes = mNoteDao.getAllNotesFromArticle(123);

        assertEquals(allNotes.get(0).getArticleId(),noteEntity.getArticleId());
        assertEquals(allNotes.size(),1);
    }

    @Test
    public void deleteNoteTest() throws Exception {
        NoteEntity noteEntity = new NoteEntity(123,"title","new note");
        int rowId = (int)mNoteDao.addNote(noteEntity);
        noteEntity.setId(rowId);
        List<NoteEntity> noteEntityList = mNoteDao.getAllNotesFromArticle(123);
        assertEquals(noteEntityList.size(), 1);
        mNoteDao.deleteNote(noteEntity);
        noteEntityList = mNoteDao.getAllNotesFromArticle(123);
        assertEquals(noteEntityList.size(), 0);


    }
}
