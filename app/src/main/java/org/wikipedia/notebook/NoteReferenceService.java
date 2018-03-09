package org.wikipedia.notebook;

import android.arch.persistence.room.Room;
import android.content.Context;

import org.wikipedia.database.room.AppDatabase;
import org.wikipedia.notebook.database.NoteDao;
import org.wikipedia.notebook.database.NoteEntity;
import org.wikipedia.notebook.database.ReferenceDao;
import org.wikipedia.notebook.database.ReferenceEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andres on 2018-03-09.
 */

public class NoteReferenceService {

    private Context context;
    private AppDatabase db;
    private NoteDao noteDao;
    private ReferenceDao referenceDao;

    public NoteReferenceService(Context context) {
        this.context = context;
        this.db = Room.inMemoryDatabaseBuilder(this.context, AppDatabase.class).build();
        this.noteDao = db.noteDao();
        this.referenceDao = db.referenceDao();
    }

    public List<Note> getAllArticleNotes(String articleId) {
        List<Note> notes = new ArrayList<Note>();
        List<NoteEntity> noteEntities = noteDao.getAllNotesFromArticle(articleId);
        List<ReferenceEntity> referenceEntities = referenceDao.getAllArticleReferences(articleId);
        for (NoteEntity ne: noteEntities) {
            Note newNote = new Note(ne.getId(), ne.getArticleId(), ne.getArticleTitle(), ne.getText());
            for (ReferenceEntity re: referenceEntities) {
                if (re.getNoteId() == newNote.getId()) {
                    newNote.addReference(new Reference(re.getReferenceNum(), re.getText()));
                }
            }
        }
        return notes;
    }

    public void addNote(Note newNote) {
        NoteEntity noteEntity = new NoteEntity(newNote.getId(), newNote.getArticleTitle(), newNote.getText());
        this.noteDao.addNote(noteEntity);
        int noteId = this.noteDao.getLastInsertedRowId();
        List<Reference> references = newNote.getAllReferences();
        List<ReferenceEntity> referenceEntities = new ArrayList<ReferenceEntity>();
        for (Reference reference : references) {
            ReferenceEntity newRE = new ReferenceEntity(newNote.getArticleid(), noteId, reference.getNumber(), reference.getText());
            referenceEntities.add(newRE);
        }
        this.referenceDao.addReferences(referenceEntities);
    }
}
