package org.wikipedia.notebook;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import org.wikipedia.database.room.AppDatabase;
import org.wikipedia.notebook.database.NoteCommentsDao;
import org.wikipedia.notebook.database.NoteCommentsEntity;
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

    public interface SaveCallback {
        void afterSave();
    }

    public interface GetNotesCallback {
        void afterGetNotes(List<Note> notes);
    }
    public interface DeleteNoteCallBack {
        void afterDeleteNote();
    }

    private Context context;
    private AppDatabase db;
    private NoteDao noteDao;
    private ReferenceDao referenceDao;
    private NoteCommentsDao noteCommentsDao;

    public NoteReferenceService(Context context) {
        this.context = context;
        this.db = AppDatabase.getInstance(context);
        this.noteDao = db.noteDao();
        this.referenceDao = db.referenceDao();
        this.noteCommentsDao = db.noteCommentsDao();
    }

    public NoteReferenceService(Context context, AppDatabase db) {
        this.context = context;
        this.db = db;
        this.noteDao = db.noteDao();
        this.referenceDao = db.referenceDao();
        this.noteCommentsDao = db.noteCommentsDao();
    }

    @SuppressLint("StaticFieldLeak")
    public void getAllArticleNotes(int articleId, GetNotesCallback callback) {
        new AsyncTask<Object, Void, List<Note>>() {

            @Override
            protected List<Note> doInBackground(Object... objects) {
                List<NoteEntity> noteEntities = noteDao.getAllNotesForArticle(articleId);
                List<ReferenceEntity> referenceEntities = referenceDao.getAllArticleReferences(articleId);
                List<NoteCommentsEntity> noteCommentsEntities;

                HashMap<Integer, Reference> mapReference = new HashMap<Integer, Reference>();
                HashMap<Integer, Note> mapNote = new HashMap<Integer, Note>();

                for (NoteEntity ne: noteEntities) {
                    Note newNote = new Note(ne.getId(), ne.getArticleId(), ne.getArticleTitle(), ne.getText());

                    //Getting all comments for the note
                    noteCommentsEntities = noteCommentsDao.getAllNoteComments(ne.getId());
                    for (NoteCommentsEntity nce: noteCommentsEntities) {
                        newNote.addNoteComment(new NoteComments(nce.getText()));
                    }

                    mapNote.put(newNote.getId(), newNote);
                    //notes.add(newNote);
                }
                for (int i = 0; i < referenceEntities.size(); i++) {
                    if (!mapReference.containsKey(referenceEntities.get(i).getReferenceNum())) {
                        mapReference.put(referenceEntities.get(i).getReferenceNum(),
                                new Reference(referenceEntities.get(i).getReferenceNum(), referenceEntities.get(i).getText()));
                    }
                }

                for (int i = 0; i < referenceEntities.size(); i++) {
                    mapNote.get(referenceEntities.get(i).getNoteId()).addReference(mapReference.get(referenceEntities.get(i).getReferenceNum()));
                }

                return new ArrayList<Note>(mapNote.values());
            }
            protected void onPostExecute(List<Note> result) {
                callback.afterGetNotes(result);
            }
        }.execute(new Object());
    }

    @SuppressLint("StaticFieldLeak")
    public void addNote(Note newNote, SaveCallback callback) {
        new AsyncTask<Object, Void, Void>() {

            @Override
            protected Void doInBackground(Object... objects) {
                NoteEntity noteEntity = new NoteEntity(newNote.getArticleid(), newNote.getArticleTitle(), newNote.getText());
                int noteId = (int)noteDao.addNote(noteEntity);
                List<Reference> references = newNote.getAllReferences();
                List<ReferenceEntity> referenceEntities = new ArrayList<ReferenceEntity>();
                for (Reference reference : references) {
                    ReferenceEntity newRE = new ReferenceEntity(newNote.getArticleid(), noteId, reference.getNumber(), reference.getText());
                    referenceEntities.add(newRE);
                }
                referenceDao.addReferences(referenceEntities);
                callback.afterSave();
                return null;
            }
        }.execute(new Object());
    }
    @SuppressLint("StaticFieldLeak")
    public void deleteNote(Note note, DeleteNoteCallBack callBack) {
        new AsyncTask<Object, Void, Void>() {

            @Override
            protected Void doInBackground(Object... objects) {
                NoteEntity noteEntity = new NoteEntity(note.getArticleid(), note.getArticleTitle(), note.getText());
                noteEntity.setId(note.getId());
                noteDao.deleteNote(noteEntity);
                callBack.afterDeleteNote();
                return null;
            }
        }.execute(new Object());
    }

    public List<String> getAllNotedArticles() {
        return noteDao.getAllArticles();
    }

    public void deleteAllNotes(String title) {
        noteDao.deleteAllNotes(title);
    }

    public boolean articleCannotDelete(Context context, String currentTitle) {
        List<String> articleTitles = getAllNotedArticles();
        boolean cannotDelete = false;
        for (String title : articleTitles) {
            if (title.equals(currentTitle)) {
                cannotDelete = true;
            }
        }
        return cannotDelete;
    }
}
