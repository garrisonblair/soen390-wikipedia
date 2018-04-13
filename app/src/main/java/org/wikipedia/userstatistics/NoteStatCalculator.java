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

    public NoteStatCalculator(AppDatabase database) {
        this.db = database;
        this.noteDao = db.noteDao();

        totalNotes = noteDao.getAllNotes();
        notedArticles = noteDao.getAllArticles();
    }

    public NoteStatCalculator(Context context) {
        this(AppDatabase.getInstance(context));
    }

    public int getTotalNotes() {
        return totalNotes.size();
    }

    public int getTotalNotedArticles() {
        return notedArticles.size();
    }

    public double getNotesPerArticle() {
        if (notedArticles.size() > 0) {
            return ((double) totalNotes.size() / notedArticles.size());
        } else {
            return 0;
        }
    }
}
