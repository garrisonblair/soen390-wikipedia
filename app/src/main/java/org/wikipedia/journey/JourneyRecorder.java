package org.wikipedia.journey;

import org.wikipedia.page.PageProperties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Stack;

import android.content.Context;
import android.util.Log;


/**
 * Created by Fred on 2018-04-02.
 */

public class JourneyRecorder {

    private Visit root;

    private Visit currentVisit;
    private Stack<Visit> pageStack;

    private static JourneyRecorder INSTANCE;

    private boolean backStackPop;


    private static String FILENAME = "journeyPersistance";
    private Context applicationContext;

    public static JourneyRecorder getInstance(Context applicationContext) {

        if (INSTANCE == null) {
            INSTANCE = new JourneyRecorder(applicationContext);
        }

        return INSTANCE;
    }

    public JourneyRecorder(Context context) {
        pageStack = new Stack<Visit>();
        backStackPop = false;
        root = null;
        applicationContext = context;
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
        persist();
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

    private void persist() {
        File file = new File(applicationContext.getFilesDir(), FILENAME);

        try {
            ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file));
            output.writeObject(root);
        } catch (Exception e) {
            Log.d("DEBUG", e.getMessage());
            Log.d("DEBUG", "Problem opening file for writing");
        }
    }

    public Visit getLastJourney() {
        File file = new File(applicationContext.getFilesDir(), FILENAME);

        try {
            ObjectInputStream input = new ObjectInputStream(new FileInputStream(file));
            Visit journey = (Visit) input.readObject();

            return journey;
        } catch (Exception e) {
            Log.d("DEBUG", e.getMessage());
            Log.d("DEBUG", "Problem opening file for reading");
        }

        return null;
    }

}
