package org.wikipedia.notebook;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andres on 2018-03-05.
 */

public class Note{

    private String text;
    private List<Reference> references;

    public Note(int articleId, String text) {
        this.text = text;
        references = new ArrayList<Reference>();
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
}
