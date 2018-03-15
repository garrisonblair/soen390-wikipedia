package org.wikipedia.notebook.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Andres on 2018-03-08.
 */
@Entity(tableName = "notes")
public class NoteEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int articleId;
    private String articleTitle;
    private String text;

    public NoteEntity(int articleId, String articleTitle,  String text) {
        this.articleId = articleId;
        this.articleTitle = articleTitle;
        this.text = text;
    }

    public int getArticleId() {
        return this.articleId;
    }

    public int getId() {
        return this.id;
    }

    public String getText() {
        return this.text;
    }

    public String getArticleTitle() { return this.articleTitle; }

    public void setArticleTitle(String articleTitle) { this.articleTitle = articleTitle; }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public void setId(int noteId) {
        this.id = noteId;
    }

    public void setText(String text) {
        this.text = text;
    }
}
