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
        for (ReferenceEntity re : referenceEntities) {
            if (!mapReference.containsKey(re.getReferenceNum())) {
                mapReference.put(re.getNoteId(), new Reference(re.getReferenceNum(), re.getText()));
            }
        }
        for (ReferenceEntity re : referenceEntities) {
            mapNote.get(re.getNoteId()).addReference(mapReference.get(re.getReferenceNum()));
            mapReference.get(re.getReferenceNum()).addNote(mapNote.get(re.getNoteId()));
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
    //Only for TEST
    public void superSedeDbForTest(AppDatabase appDatabase) {
        db = appDatabase;
        noteDao = appDatabase.noteDao();
        referenceDao = appDatabase.referenceDao();
    }
}
