package org.wikipedia.notes;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;

import org.wikipedia.R;
import org.wikipedia.WikipediaApp;
import org.wikipedia.activity.BaseActivity;
import org.wikipedia.util.ResourceUtil;

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

    public SpannableStringBuilder annotate(String note, String spans) {
        SpannableStringBuilder annotatedNote = new SpannableStringBuilder(note);
        int start;
        String s;
        int end;
        String e;
        int numSpans;
        int i = 0;
        while (i < spans.length()) {
            i += 1; // Leave '['
            s = "";
            e = "";
            while (spans.charAt(i) != '.') {
                s += spans.charAt(i++);
            }
            i += 1; // Leave '.'
            start = Integer.parseInt(s);
            while (spans.charAt(i) != '.') {
                e += spans.charAt(i++);
            }
            i += 1; // Leave '.'
            end = Integer.parseInt(e);
            numSpans = Integer.parseInt(String.valueOf(spans.charAt(i++)));
            if (numSpans != 0) {
                for (int j = 0; j < numSpans; j++) {
                    switch (spans.charAt(i++)) {
                        case 'b':
                            annotatedNote.setSpan(
                                    new StyleSpan(Typeface.BOLD),
                                    start,
                                    end,
                                    SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE);
                            break;
                        case 'i':
                            annotatedNote.setSpan(
                                    new StyleSpan(Typeface.ITALIC),
                                    start,
                                    end,
                                    SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE);
                            break;
                        case 'u':
                            annotatedNote.setSpan(
                                    new UnderlineSpan(),
                                    start,
                                    end,
                                    SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE);
                            break;
                        default:
                            break;
                    }
                }
            }
            i += 1; // Leave ']'
        }
        return annotatedNote;
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
