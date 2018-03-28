package org.wikipedia.notebook.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by Ken on 2018-03-27.
 */
@Dao
public interface NoteCommentsDao {

    @Query("SELECT * FROM `noteComments` WHERE noteId = :noteId ORDER BY ncid ASC")
    List<NoteCommentsEntity> getAllNoteComments(int noteId);

    @Query("UPDATE `noteComments` SET text = :text WHERE ncid = :ncid")
    void updateNoteComments(int ncid, String text);

    @Insert
    void addNoteComments(NoteCommentsEntity noteComment);

    @Delete
    void delete(NoteCommentsEntity noteComment);

}
