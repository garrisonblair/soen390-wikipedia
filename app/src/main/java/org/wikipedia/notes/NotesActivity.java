package org.wikipedia.notes;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.wikipedia.R;
import org.wikipedia.WikipediaApp;
import org.wikipedia.activity.BaseActivity;
import org.wikipedia.util.ResourceUtil;

import butterknife.OnClick;

public class NotesActivity extends BaseActivity {

    private WikipediaApp app;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (WikipediaApp) getApplicationContext();
        setStatusBarColor(ResourceUtil.getThemedAttributeId(this, R.attr.page_status_bar_color));

        setContentView(R.layout.activity_note);
        openNotesFragment();
    }

    private void openNotesFragment(@NonNull String pageId, @NonNull String pageTitle) {
        NotesFragment fragment = notesFragment();

        if (fragment == null) {
            fragment = NotesFragment.newInstance(pageTitle, pageId);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.activity_note_container, fragment)
                    .commit();
        }
    }

    private void openNotesFragment() {
        NotesFragment fragment = notesFragment();

        if (fragment == null) {
            fragment = NotesFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.activity_note_container, fragment)
                    .commit();
        }
    }

    @Nullable
    private NotesFragment notesFragment() {
        return (NotesFragment) getSupportFragmentManager()
                .findFragmentById(R.id.activity_note_container);
    }

    @Override
    public void onBackPressed() {
        app.getSessionFunnel().backPressed();
        super.onBackPressed();
        finish();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        app.getSessionFunnel().persistSession();
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
