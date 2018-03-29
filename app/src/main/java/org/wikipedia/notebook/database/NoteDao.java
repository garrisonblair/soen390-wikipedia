package org.wikipedia.notebook.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by Andres on 2018-03-08.
 */
@Dao
public interface NoteDao {

    @Query("SELECT * FROM notes WHERE articleId = :articleId ORDER BY id ASC")
    List<NoteEntity> getAllNotesForArticle(int articleId);

    @Query("SELECT DISTINCT articleTitle FROM notes")
    List<String> getAllArticles();

    @Insert
    long addNote(NoteEntity note);

    @Delete
    void deleteNote(NoteEntity note);

    @Update
    void updateNote(NoteEntity note);

    @Query("DELETE FROM notes WHERE articleTitle = :articleTitle")
    void deleteAllNotes(String articleTitle);

    @Query("UPDATE notes SET comment = :comment WHERE id = :noteId")
    void updateComment(String comment, int noteId);
}

