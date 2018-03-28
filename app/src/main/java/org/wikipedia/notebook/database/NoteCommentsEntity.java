package org.wikipedia.notebook.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Created by Ken on 2018-03-27.
 */

@Entity(tableName = "noteComments",
        foreignKeys = @ForeignKey(entity = NoteEntity.class,
                                  parentColumns = "id",
                                  childColumns = "noteId",
                                  onDelete = CASCADE))
public class NoteCommentsEntity {

    @PrimaryKey (autoGenerate = true)
    private int ncid;

    private int noteId;
    private String text;

    public NoteCommentsEntity(int noteId, String text) {
        this.noteId = noteId;
        this.text = text;
    }

    public void setNcid(int ncid) {
        this.ncid = ncid;
    }

    public int getNcid() {
        return this.ncid;
    }

    public int getNoteId() {
        return this.noteId;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
