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
import org.wikipedia.notebook.database.ReferenceDao;
import org.wikipedia.notebook.database.ReferenceEntity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Andres on 2018-03-08.
 */

@RunWith(AndroidJUnit4.class)
public class ReferenceDaoTest {
    private NoteDao mNoteDao;
    private ReferenceDao mRDao;
    private AppDatabase mDb;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getTargetContext();
        mDb = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        mNoteDao = mDb.noteDao();
        mRDao = mDb.referenceDao();
    }

    @After
    public void closeDb() throws IOException {
        mDb.close();
    }

    @Test
    public void writeNoteAndReadInList() throws Exception {
        NoteEntity noteEntity = new NoteEntity(123,"title","new note");
        int row = (int)mNoteDao.addNote(noteEntity);
        ReferenceEntity referenceEntity = new ReferenceEntity(123,row,1,"reference text");
        ReferenceEntity referenceEntity2 = new ReferenceEntity(123,row,2,"reference text 2");
        List<ReferenceEntity> referenceEntityList = new ArrayList<ReferenceEntity>();
        referenceEntityList.add(referenceEntity);
        referenceEntityList.add(referenceEntity2);

        mRDao.addReferences(referenceEntityList);

        List<ReferenceEntity> allreferences = mRDao.getAllArticleReferences(123);
        assertEquals(allreferences.size(), 2);
        assertEquals(allreferences.get(0).getNoteId(),row);
    }


}
