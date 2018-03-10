package org.wikipedia.notes;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;


import org.wikipedia.R;
import org.wikipedia.WikipediaApp;
import org.wikipedia.activity.BaseActivity;
import org.wikipedia.page.PageToolbarHideHandler;
import org.wikipedia.util.ResourceUtil;

import java.util.ArrayList;

public class NotesActivity extends BaseActivity {

    private NotesFragment notesFragment;
    private String pageId;
    private String pageTitle;
    private WikipediaApp app;
    private PageToolbarHideHandler toolbarHideHandler;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (WikipediaApp) getApplicationContext();
        setStatusBarColor(ResourceUtil.getThemedAttributeId(this, R.attr.page_status_bar_color));

        Bundle bundleRead = getIntent().getExtras();
        pageId = bundleRead.getString("pageId");
        pageTitle = bundleRead.getString("pageTitle");

//        ArrayList<String> notes = new ArrayList();
//
//        notes.add("Material Design Icons' growing icon collection allows designers and developers targeting various platforms to download icons in the format, color and size they need for any project.");
//        notes.add("The app is primarily being developed by the Wikimedia Foundation's Mobile Apps team. This README provides high-level guidelines for getting started with the project.");

//        Bundle bundleWrite = new Bundle();
//        bundleWrite.putString("pageTitle", pageTitle);
//        bundleWrite.putStringArrayList("notes", notes);

//        notesFragment = (NotesFragment) getSupportFragmentManager().findFragmentById(R.id.note_fragment);
//        notesFragment.setArguments(bundleWrite);

        setContentView(R.layout.activity_note);

        Log.i("pageId", pageId);
    }

}
