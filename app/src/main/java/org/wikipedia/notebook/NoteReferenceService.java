package org.wikipedia.notebook;

import android.arch.persistence.room.Room;
import android.content.Context;

import org.wikipedia.database.room.AppDatabase;
import org.wikipedia.notebook.database.NoteDao;
import org.wikipedia.notebook.database.NoteEntity;
import org.wikipedia.notebook.database.ReferenceDao;
import org.wikipedia.notebook.database.ReferenceEntity;

import java.util.ArrayList;
import java.util.HashMap;
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

    public NoteReferenceService(Context context, AppDatabase db) {
        this.context = context;
        this.db = db;
        this.noteDao = db.noteDao();
        this.referenceDao = db.referenceDao();
    }

    public List<Note> getAllArticleNotes(int articleId) {
        List<NoteEntity> noteEntities = noteDao.getAllNotesFromArticle(articleId);
        List<ReferenceEntity> referenceEntities = referenceDao.getAllArticleReferences(articleId);
        HashMap<Integer, Reference> mapReference = new HashMap<Integer, Reference>();
        HashMap<Integer, Note> mapNote = new HashMap<Integer, Note>();
        /*if (noteEntities.isEmpty()) {
            return new ArrayList<Note>();
        }*/

        for (NoteEntity ne: noteEntities) {
            Note newNote = new Note(ne.getId(), ne.getArticleId(), ne.getArticleTitle(), ne.getText());
            mapNote.put(newNote.getId(), newNote);
            //notes.add(newNote);
        }
        for (int i = 0; i< referenceEntities.size(); i++) {
            if (!mapReference.containsKey(referenceEntities.get(i).getReferenceNum())) {
                mapReference.put(referenceEntities.get(i).getNoteId(),
                        new Reference(referenceEntities.get(i).getReferenceNum(), referenceEntities.get(i).getText()));
            }
        }

        for (int i = 0; i < referenceEntities.size(); i++) {
            mapNote.get(referenceEntities.get(i).getNoteId()).addReference(mapReference.get(referenceEntities.get(i).getReferenceNum()));
            mapReference.get(referenceEntities.get(i).getReferenceNum()).addNote(mapNote.get(referenceEntities.get(i).getNoteId()));
        }

        return new ArrayList<Note>(mapNote.values());
    }

    public void addNote(Note newNote) {
        NoteEntity noteEntity = new NoteEntity(newNote.getId(), newNote.getArticleTitle(), newNote.getText());
        int noteId = (int)this.noteDao.addNote(noteEntity);
        List<Reference> references = newNote.getAllReferences();
        List<ReferenceEntity> referenceEntities = new ArrayList<ReferenceEntity>();
        for (Reference reference : references) {
            ReferenceEntity newRE = new ReferenceEntity(newNote.getArticleid(), noteId, reference.getNumber(), reference.getText());
            referenceEntities.add(newRE);
        }
        this.referenceDao.addReferences(referenceEntities);
    }

    public void deleteNote(Note note) {
        NoteEntity noteEntity = new NoteEntity(note.getArticleid(), note.getArticleTitle(), note.getText());
        this.noteDao.deleteNote(noteEntity);
    }
}
