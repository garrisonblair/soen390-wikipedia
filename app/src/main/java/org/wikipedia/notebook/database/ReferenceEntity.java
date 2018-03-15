package org.wikipedia.notebook.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Created by Andres on 2018-03-08.
 */

@Entity(tableName = "references",
        primaryKeys = {"noteId", "referenceNum"},
        foreignKeys = @ForeignKey(entity = NoteEntity.class,
                                  parentColumns = "id",
                                  childColumns = "noteId",
                                  onDelete = CASCADE))
public class ReferenceEntity {
    private int noteId;
    private int referenceNum;
    private int articleId;
    private String text;

    public ReferenceEntity(int articleId, int noteId, int referenceNum, String text) {
        this.noteId = noteId;
        this.articleId = articleId;
        this.referenceNum = referenceNum;
        this.text = text;
    }

    public int getNoteId() {
        return this.noteId;
    }

    public int getArticleId() {
        return this.articleId;
    }
    public int getReferenceNum() {
        return this.referenceNum;
    }

    public String getText() {
        return this.text;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    public void setReferenceNum(int referenceNum) {
        this.referenceNum = referenceNum;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }
}
