package org.wikipedia.notebook;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by Fred on 2018-03-06.
 */

public class ReferenceTest {

    @Test
    public void testReferenceCreation() {
        Reference ref = new Reference(1, "text");

        assertEquals(1, ref.getNumber());
        assertEquals("text", ref.getText());
        assertEquals(new ArrayList<Note>(), ref.getNotes());
    }
}
