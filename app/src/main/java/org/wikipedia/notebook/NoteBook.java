package org.wikipedia.note;

import java.util.HashMap;

/**
 * Created by Andres on 2018-03-05.
 */

public final class NoteBook {

    private HashMap<String, NotePage> notebook;
    private static NoteBook INSTANCE;

    private NoteBook() {
        notebook = new HashMap<String, NotePage>();
    }

    public static NoteBook getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new NoteBook();
        }
        return INSTANCE;
    }

    public boolean addNote(String pageId, Note note) {
        if (this.notebook.containsKey(pageId)) {
            return this.notebook.get(pageId).addNote(note);
        } else {
            NotePage newPage = new NotePage(pageId);
            if (newPage.addNote(note)) {
                this.notebook.put(pageId, newPage);
                return true;
            }
        }
        return false;
    }
}
