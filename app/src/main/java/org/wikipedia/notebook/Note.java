package org.wikipedia.notebook;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andres on 2018-03-05.
 */

public class Note{

    private String originalText;
    private String updatedText;
    private List<Reference> references;
    private String articleTitle;
    private int articleid;
    private int id;
    private String comment;

    public Note(int articleid, String articleTitle, String text) {
        this.originalText = text;
        this.articleTitle = articleTitle;
        this.articleid = articleid;
        this.references = new ArrayList<Reference>();
    }

    public Note(int id, int articleid, String articleTitle, String text) {
        this.id = id;
        this.articleid = articleid;
        this.articleTitle = articleTitle;
        this.originalText = text;
        this.references = new ArrayList<Reference>();
    }

    public void addReference(Reference reference) {
        this.references.add(reference);
    }

    public boolean removeReference(Reference reference) {
        return this.references.remove(reference);
    }

    public String getText() {
        if (updatedText != null)
            return this.updatedText;
        return this.originalText;
    }

    public int getId() {
        return id;
    }

    public int getArticleid() {
        return articleid;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void updateText(String newText) {
        this.updatedText = newText;
    }

    public String getOriginalText() {
        return this.originalText;
    }

    public String getUpdatedText() {
        return this.updatedText;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void deleteComment() {
        this.comment = null;
    }

    public void resetToOriginalText(){
        this.updatedText = null;
    }

    public boolean isTextUpdated() {
        if (this.updatedText == null)
            return false;
        return true;
    }
    public List<Reference> getAllReferences() { return this.references; }
}
