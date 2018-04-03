package org.wikipedia.journey;

import android.util.Log;

import org.wikipedia.page.PageProperties;

import java.util.Stack;

/**
 * Created by Fred on 2018-04-02.
 */

public class JourneyRecorder {

    private Visit root;

    private Visit currentVisit;
    private Stack<Visit> pageStack;

    private static JourneyRecorder INSTANCE;

    private boolean backStackPop;

    public static JourneyRecorder getInstance() {

        if (INSTANCE == null) {
            INSTANCE = new JourneyRecorder();
        }

        return INSTANCE;
    }

    public JourneyRecorder() {
        pageStack = new Stack<Visit>();
        backStackPop = false;
        root = null;
    }

    public void visitPage(PageProperties page) {
        if (!isJourneyInProgress()) {
            startJourney(page);
            return;
        } else if (backStackPop) {
            backStackPop = false;
            return;
        }

        Visit nextVisit = new Visit(page);

        currentVisit.addSubVisit(nextVisit);

        pageStack.push(currentVisit);

        currentVisit = nextVisit;
    }

    public void leavePage() {

        if (!pageStack.empty()) {
            backStackPop = true;

            currentVisit = pageStack.pop();
            return;
        }

        //else journey done
        Log.d("DEV", getJourneyString());
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

    public String getJourneyString() {

        return root.toString(0);
    }

}
