package org.wikipedia.userstatistics;

import android.content.Context;

import org.wikipedia.database.room.AppDatabase;
import org.wikipedia.notebook.database.NoteDao;
import org.wikipedia.notebook.database.NoteEntity;

import java.util.List;

public class NoteStatCalculator {

    private AppDatabase db;
    private NoteDao noteDao;
    private List<NoteEntity> totalNotes;
    private List<String> notedArticles;

    public NoteStatCalculator(Context context) {
        this.db = AppDatabase.getInstance(context);
        this.noteDao = db.noteDao();

        totalNotes = noteDao.getAllNotes();
        notedArticles = noteDao.getAllArticles();
    }

    public int getTotalNotes() {
        return totalNotes.size();
    }

    public int getTotalNotedArticles() {
        return notedArticles.size();
    }

    public double getNotesPerArticle() {
        return totalNotes.size() / notedArticles.size();
    }
}
