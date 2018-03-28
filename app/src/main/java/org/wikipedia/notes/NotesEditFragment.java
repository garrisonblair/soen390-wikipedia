package org.wikipedia.notes;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.SpannableStringBuilder;
import android.text.style.CharacterStyle;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.wikipedia.R;
import org.wikipedia.notebook.NoteReferenceService;


public class NotesEditFragment extends Fragment {

    private SpannableStringBuilder note;
    private String title;
    private String noteSpans;
    private int pageId;
    private NoteReferenceService noteReferenceService;
    private View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String noteText = "";

        if (getActivity().getIntent() != null) {
            if (getActivity().getIntent().getExtras() != null) {
                Bundle bundleRead = getActivity().getIntent().getExtras();
                title = bundleRead.getString("pageTitle");
                pageId = bundleRead.getInt("pageId");
            }
        }
        if (getArguments() != null) {
            noteText = getArguments().getString("note");
            noteSpans = getArguments().getString("spans");
        }
        note = ((NotesActivity)getActivity()).annotate(noteText, noteSpans);
    }

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        view = inflater.inflate(R.layout.fragment_notes_edit, container, false);

        TextView editTitle = view.findViewById(R.id.note_edit_title);
        editTitle.setText(title);

        TextView editBody = view.findViewById(R.id.note_edit_body);
        editBody.setText(note);

        // Setting text to bold button
        ImageButton bold = view.findViewById(R.id.icon_bold);
        bold.setOnClickListener(v -> {
            if (editBody.getSelectionStart() != editBody.getSelectionEnd()) {
                int start = editBody.getSelectionStart();
                int end = editBody.getSelectionEnd();
                note.setSpan(new StyleSpan(Typeface.BOLD), start, end, SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE);
                editBody.setText(note);
            } else {
                Toast.makeText(getContext(), "Select text to make bold first", Toast.LENGTH_SHORT).show();
            }
        });

        // Setting text to italics button
        ImageButton italics = view.findViewById(R.id.icon_italics);
        italics.setOnClickListener(v -> {
            if (editBody.getSelectionStart() != editBody.getSelectionEnd()) {
                int start = editBody.getSelectionStart();
                int end = editBody.getSelectionEnd();
                note.setSpan(new StyleSpan(Typeface.ITALIC), start, end, SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE);
                editBody.setText(note);
            } else {
                Toast.makeText(getContext(), "Select text to italicize first", Toast.LENGTH_SHORT).show();
            }
        });

        // Setting text to underlined button
        ImageButton underline = view.findViewById(R.id.icon_underline);
        underline.setOnClickListener(v -> {
            if (editBody.getSelectionStart() != editBody.getSelectionEnd()) {
                int start = editBody.getSelectionStart();
                int end = editBody.getSelectionEnd();
                note.setSpan(new UnderlineSpan(), start, end, SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE);
                editBody.setText(note);
            } else {
                Toast.makeText(getContext(), "Select text to underline first", Toast.LENGTH_SHORT).show();
            }
        });

        // Re-setting selected text button
        ImageButton undo = view.findViewById(R.id.icon_undo);
        undo.setOnClickListener(v -> {
            if (editBody.getSelectionStart() != editBody.getSelectionEnd()) {
                int start = editBody.getSelectionStart();
                int end = editBody.getSelectionEnd();
                CharacterStyle[] spansToRemove = note.getSpans(start, end, CharacterStyle.class);
                for (CharacterStyle span: spansToRemove) {
                    if (span != null) {
                        note.removeSpan(span);
                    }
                }
                editBody.setText(note);
            } else {
                Toast.makeText(getContext(), "Select text to reset to normal first", Toast.LENGTH_SHORT).show();
            }
        });

        // Re-setting selected text button
        ImageButton done = view.findViewById(R.id.icon_done);
        done.setOnClickListener(v -> {
            StringBuilder saved = new StringBuilder();

            int next;
            for (int i = 0; i < note.length(); i = next) {

                // find the next span transition
                next = note.nextSpanTransition(i, note.length(), CharacterStyle.class);
                int spanEnd = next - 1;

                // get all spans in this range
                int numOfSpans = 0;
                StringBuilder spanTypes = new StringBuilder();
                CharacterStyle[] spans = note.getSpans(i, next, CharacterStyle.class);
                for (CharacterStyle span1 : spans) {
                    if (span1 instanceof StyleSpan) {
                        StyleSpan span = (StyleSpan) span1;
                        if (span.getStyle() == 1) {
                            spanTypes.append("b");
                        }
                        if (span.getStyle() == 2) {
                            spanTypes.append("i");
                        }
                    }
                    if (span1 instanceof UnderlineSpan) {
                        spanTypes.append("u");
                    }
                    numOfSpans++;
                }

                saved.append(i).append(".").append(spanEnd).append(".").append(numOfSpans).append(spanTypes);
            }
            Log.i("DEBUG SPANS", saved.toString());

            // TODO: update & save edited note state

            getFragmentManager().popBackStackImmediate();
        });

        return view;
    }

    @NonNull
    public static NotesEditFragment newInstance(String note, String spans) {
        NotesEditFragment fragment = new NotesEditFragment();

        Bundle args = new Bundle();
        args.putString("note", note);
        args.putString("spans", spans);

        fragment.setArguments(args);
        return fragment;
    }
}
