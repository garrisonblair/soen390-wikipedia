package org.wikipedia.notebook.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by Andres on 2018-03-08.
 */
@Dao
public interface NoteDao {

    @Query("SELECT * FROM notes WHERE articleId = :articleId ORDER BY id ASC")
    public List<NoteEntity> getAllNotesFromArticle(String articleId);

    @Insert
    public void addNote(NoteEntity note);

    @Query("SELECT ID FROM notes WHERE ID = (SELECT MAX(ID)  FROM notes);")
    public int getLastInsertedRowId();

    @Delete
    public void deleteNote(NoteEntity note);

}

