package org.wikipedia.notebook;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andres on 2018-03-05.
 */

public class Note{

    private String text;
    private List<Reference> references;
    private List<NoteComments> noteComments;
    private String articleTitle;
    private int articleid;
    private int id;

    public Note(int articleid, String articleTitle, String text) {
        this.text = text;
        this.articleTitle = articleTitle;
        this.articleid = articleid;
        this.references = new ArrayList<Reference>();
        this.noteComments = new ArrayList<NoteComments>();
    }

    public Note(int id, int articleid, String articleTitle, String text) {
        this.id = id;
        this.articleid = articleid;
        this.articleTitle = articleTitle;
        this.text = text;
        this.references = new ArrayList<Reference>();
        this.noteComments = new ArrayList<NoteComments>();
    }

    public void addReference(Reference reference) {
        this.references.add(reference);
    }

    public boolean removeReference(Reference reference) {
        return this.references.remove(reference);
    }

    public String getText() {
        return this.text;
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
    public List<Reference> getAllReferences() {
        return this.references;
    }

    //Note Comments operations
    public void addNoteComment(NoteComments noteComment) {
        this.noteComments.add(noteComment);
    }

    public boolean removeNoteComment(NoteComments noteComment) {
        return this.noteComments.remove(noteComment);
    }

    public List<NoteComments> getAllNoteComments() {
        return this.noteComments;
    }
}
