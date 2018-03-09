package org.wikipedia.notebook;

import java.util.List;

/**
 * Created by Fred on 2018-03-06.
 */

public class SelectionResult {
    private List<Reference> references;
    private String selectionText;

    public List<Reference> getReferences() {
        return references;
    }

    public String getSelectionText() {
        return selectionText;
    }
}
