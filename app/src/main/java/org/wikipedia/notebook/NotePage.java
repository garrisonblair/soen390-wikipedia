package org.wikipedia.note;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andres on 2018-03-05.
 */

public class NotePage {
    private String pageId;
    private List<Note> notes;

    public NotePage(String pageId) {
        this.pageId = pageId;
        notes = new ArrayList<Note>();
    }
    public List<Note> getNotes() {
        return this.notes;
    }
    public boolean addNote(Note note) {
        return this.notes.add(note);
    }
    public boolean removeNote(Note note) {
        return this.notes.remove(note);
    }
}
