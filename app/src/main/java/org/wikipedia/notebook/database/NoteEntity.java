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
    private String updatedText;
    private String comment;
    private String spam;

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

    public String getComment() {
        return this.comment;
    }

    public String getText() {
        return this.text;
    }

    public String getSpam() {
        return this.spam;
    }

    public String getArticleTitle() {
        return this.articleTitle;
    }

    public String getUpdatedText() { return this.updatedText; }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setSpam(String spam) {
        this.spam = spam;
    }

    public void setId(int noteId) {
        this.id = noteId;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setUpdatedText(String newText) { this.updatedText = newText; }

}
