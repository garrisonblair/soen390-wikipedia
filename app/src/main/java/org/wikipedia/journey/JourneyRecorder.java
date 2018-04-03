package org.wikipedia.journey;

import org.wikipedia.page.PageProperties;

import java.util.Stack;

/**
 * Created by Fred on 2018-04-02.
 */

public class JourneyRecorder {

    private Visit root = null;

    private Visit currentVisit;
    private Stack<Visit> pageStack = new Stack<Visit>();

    private static JourneyRecorder INSTANCE;

    public static JourneyRecorder getInstance() {

        if (INSTANCE == null) {
            INSTANCE = new JourneyRecorder();
        }

        return INSTANCE;
    }

    public void visitPage(PageProperties page) {
        if (!isJourneyInProgress()) {
            return;
        }

        Visit nextVisit = new Visit(page);

        currentVisit.addSubVisit(nextVisit);

        pageStack.push(currentVisit);

        currentVisit = nextVisit;
    }

    public void leavePage() {

        if (!pageStack.empty()) {
            currentVisit = pageStack.pop();
            return;
        }

        //else journey done
        root = null;
        return;
    }

    public void startJourney(PageProperties startPage) {
        root = new Visit(startPage);
        currentVisit = root;
    }

    public boolean isJourneyInProgress() {
        return root != null;
    }

}
