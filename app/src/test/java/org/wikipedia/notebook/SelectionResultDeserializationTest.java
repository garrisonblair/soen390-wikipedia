package org.wikipedia.notebook;

import com.google.gson.Gson;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Created by Fred on 2018-03-06.
 */

public class SelectionResultDeserializationTest {

    @Test
    public void testDeserializingSelectionResult() {

        String json = "{\"selectionText\": \"This is a note\", \"references\": [{\"number\": 1, \"text\": \"reference1\"},{\"number\": 2, \"text\": \"reference2\"}]}";

        Gson gson = new Gson();

        SelectionResult result = gson.fromJson(json, SelectionResult.class);

        assertEquals("This is a note", result.getSelectionText());
        assertEquals(2, result.getReferences().size());

        Reference ref1 = result.getReferences().get(0);
        assertEquals(1, ref1.getNumber());
        assertEquals("reference1", ref1.getText());

        Reference ref2 = result.getReferences().get(1);
        assertEquals(2, ref2.getNumber());
        assertEquals("reference2", ref2.getText());

    }
}
