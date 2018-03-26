package org.wikipedia.notes;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.wikipedia.R;
import org.wikipedia.notebook.NoteReferenceService;


public class NotesEditFragment extends Fragment {

    private SpannableString note;
    private String title;
    private int pageId;
    private NoteReferenceService noteReferenceService;
    private View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i("DEBUG", "FRAGMENT CREATED");
        if (getActivity().getIntent().getExtras() != null) {
            Bundle bundleRead = getActivity().getIntent().getExtras();
            title = bundleRead.getString("pageTitle");
            pageId = bundleRead.getInt("pageId");
            note = new SpannableString(bundleRead.getString("noteText"));
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        view = inflater.inflate(R.layout.fragment_notes_edit, container, false);

        TextView editTitle = view.findViewById(R.id.note_edit_title);
        editTitle.setText(title);

        TextView editBody = view.findViewById(R.id.note_edit_body);
        editBody.setText(note);

        Log.i("DEBUG", "VIEW CREATED");

        return view;
    }

    @NonNull
    public static NotesEditFragment newInstance() {
        NotesEditFragment fragment = new NotesEditFragment();
        return fragment;
    }
}
