package org.wikipedia.notebook;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

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

    public NoteReferenceService(Context context) {
        this.context = context;
        this.db = AppDatabase.getInstance(context);
        this.noteDao = db.noteDao();
        this.referenceDao = db.referenceDao();
    }

    public NoteReferenceService(Context context, AppDatabase db) {
        this.context = context;
        this.db = db;
        this.noteDao = db.noteDao();
        this.referenceDao = db.referenceDao();
    }

    @SuppressLint("StaticFieldLeak")
    public void getAllArticleNotes(int articleId, GetNotesCallback callback) {
        new AsyncTask<Object, Void, List<Note>>() {

            @Override
            protected List<Note> doInBackground(Object... objects) {
                List<NoteEntity> noteEntities = noteDao.getAllNotesForArticle(articleId);
                List<ReferenceEntity> referenceEntities = referenceDao.getAllArticleReferences(articleId);
                HashMap<Integer, Reference> mapReference = new HashMap<Integer, Reference>();
                HashMap<Integer, Note> mapNote = new HashMap<Integer, Note>();

                for (NoteEntity ne: noteEntities) {
                    Note newNote = new Note(ne.getId(), ne.getArticleId(), ne.getArticleTitle(), ne.getText());
                    mapNote.put(newNote.getId(), newNote);
                    //notes.add(newNote);
                }
                for (int i = 0; i < referenceEntities.size(); i++) {
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
        //NoteReferenceService noteReferenceService = new NoteReferenceService(getContext().getApplicationContext());
        //List<String> articleTitles = noteReferenceService.getAllNotedArticles();
        Toast.makeText(context, "Title: " + currentTitle, Toast.LENGTH_SHORT).show();

        List<String> articleTitles = getAllNotedArticles();
        boolean cannotDelete = false;
        for (String title : articleTitles) {
            //if (model.getPage().getTitle().getText() == title) {
            if (title == currentTitle) {
                //Toast.makeText(getContext(), "The article contains note(s), cannot be deleted.", Toast.LENGTH_LONG).show();
                cannotDelete = true;
            }
        }
        Toast.makeText(context, "Title: " + cannotDelete, Toast.LENGTH_SHORT).show();
        return cannotDelete;
    }
}
