package org.wikipedia.notebook;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andres on 2018-03-05.
 */

public class Reference {
    private int number;
    private String text;
    private List<Note> notes;

    public Reference(int number, String text) {
        this.number = number;
        this.text = text;
        notes = new ArrayList<Note>();
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void addNote(Note note) { this.notes.add(note); }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }
}
