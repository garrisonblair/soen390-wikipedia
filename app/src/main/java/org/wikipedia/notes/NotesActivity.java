package org.wikipedia.notes;


import android.os.Bundle;
import android.util.Log;


import org.wikipedia.R;
import org.wikipedia.WikipediaApp;
import org.wikipedia.activity.BaseActivity;
import org.wikipedia.page.PageToolbarHideHandler;
import org.wikipedia.util.ResourceUtil;

public class NotesActivity extends BaseActivity {

    private NotesFragment notesFragment;
    private String pageId;
    private WikipediaApp app;
    private PageToolbarHideHandler toolbarHideHandler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (WikipediaApp) getApplicationContext();

        setContentView(R.layout.activity_note);

        notesFragment = (NotesFragment) getSupportFragmentManager().findFragmentById(R.id.note_fragment);

        setStatusBarColor(ResourceUtil.getThemedAttributeId(this, R.attr.page_status_bar_color));

        Bundle bundle = getIntent().getExtras();
        pageId = bundle.getString("pageId");

        Log.i("pageId", pageId);
    }

}
