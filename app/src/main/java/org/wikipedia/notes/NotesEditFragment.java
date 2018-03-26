package org.wikipedia.notes;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

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

        if (getActivity().getIntent().getExtras() != null) {
            Bundle bundleRead = getActivity().getIntent().getExtras();
            title = bundleRead.getString("pageTitle");
            pageId = bundleRead.getInt("pageId");
        }
        if (getArguments() != null) {
            note = new SpannableString(getArguments().getString("note"));
        }

//
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        view = inflater.inflate(R.layout.fragment_notes_edit, container, false);

        TextView editTitle = view.findViewById(R.id.note_edit_title);
        editTitle.setText(title);

        TextView editBody = view.findViewById(R.id.note_edit_body);
        editBody.setText(note);

        // Setting text to bold button
        ImageButton bold = view.findViewById(R.id.icon_bold);
        bold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("SELECTION START", Integer.toString(editBody.getSelectionStart()));
                Log.i("SELECTION END", Integer.toString(editBody.getSelectionEnd()));
                if (editBody.getSelectionStart() != editBody.getSelectionEnd()) {
                    int start = editBody.getSelectionStart();
                    int end = editBody.getSelectionEnd();
                    note.setSpan(new StyleSpan(Typeface.BOLD), start, end, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
                    editBody.setText(note);
                } else {
                    Toast.makeText(getContext(), "Select text to make bold first ", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    @NonNull
    public static NotesEditFragment newInstance(String note) {
        NotesEditFragment fragment = new NotesEditFragment();

        Bundle args = new Bundle();
        args.putString("note", note);

        fragment.setArguments(args);
        return fragment;
    }
}
