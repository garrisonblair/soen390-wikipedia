package org.wikipedia.notebook;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


/**
 * Created by Andres on 2018-03-27.
 */

public class NoteTest {
    @Test
    public void isTextUpdatedTest() {
        Note note = new Note(1,2,"title","original text");
        assertTrue(!note.isTextUpdated());
        note.updateText("updated text");
        assertTrue(note.isTextUpdated());
    }

    public void getTextTest() {
        Note note = new Note(1,2,"title","original text");
        assertEquals("original text", note.getText());
        note.updateText("updated text");
        assertEquals("updated text", note.getText());
        note.resetToOriginalText();
        assertEquals("original text", note.getText());
    }
}
