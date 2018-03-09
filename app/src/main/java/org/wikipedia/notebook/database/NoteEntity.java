package org.wikipedia.notebook.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andres on 2018-03-08.
 */
@Entity(tableName = "notes")
public class NoteEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String articleId;
    private String text;

    public NoteEntity(String articleId, String text) {
        this.articleId = articleId;
        this.text = text;
    }

    public String getArticleId() {
        return this.articleId;
    }

    public int getId() {
        return this.id;
    }

    public String getText() {
        return this.text;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public void setId(int noteId) {
        this.id = noteId;
    }

    public void setText(String text) {
        this.text = text;
    }
}
