package org.wikipedia.userstatistics;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.wikipedia.database.room.AppDatabase;
import org.wikipedia.notebook.database.NoteDao;
import org.wikipedia.notebook.database.NoteEntity;

import java.io.IOException;


@RunWith(AndroidJUnit4.class)
public class NoteStatCalculatorTest {

    private Context context;
    private NoteDao noteDao;
    private AppDatabase db;

    @Before
    public void createDb() {
        this.context = InstrumentationRegistry.getTargetContext();
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        noteDao = db.noteDao();
    }

    @Before
    public void populateDb() {
        NoteEntity note1 = new NoteEntity(
                1234,
                "Article 1",
                "Article 1 text 1"
        );
        noteDao.addNote(note1);

        NoteEntity note2 = new NoteEntity(
                1234,
                "Article 1",
                "Article 1 text 2"
        );
        noteDao.addNote(note2);

        NoteEntity note3 = new NoteEntity(
                4567,
                "Article 2",
                "Article 2 text 1"
        );
        noteDao.addNote(note3);
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    @Test
    public void testNoteStatCalculator() throws Exception {

        NoteStatCalculator statCalculator = new NoteStatCalculator(db);

        // Total number of notes should be 3
        Assert.assertEquals(3, statCalculator.getTotalNotes());
        // Total noted articles should be 2
        Assert.assertEquals(2, statCalculator.getTotalNotedArticles());
        // Notes per Article ratio should be 3/2 ie 1.5
        Assert.assertEquals(1.5, statCalculator.getNotesPerArticle(), 0);
    }

}