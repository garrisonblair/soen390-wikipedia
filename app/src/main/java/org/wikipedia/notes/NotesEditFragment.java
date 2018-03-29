package org.wikipedia.notes;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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
import org.wikipedia.notebook.Note;
import org.wikipedia.notebook.NoteReferenceService;

import java.util.List;

public class NotesEditFragment extends Fragment {

    private SpannableStringBuilder note;
    private String title;
    private String noteSpans;
    private int pageId;
    private int noteId;
    private NoteReferenceService noteReferenceService;
    private Note noteInstance;
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

        // Get data passed from SingleNoteFragment
        if (getArguments() != null) {
            noteText = getArguments().getString("note");
            noteSpans = getArguments().getString("spans");
            noteId = getArguments().getInt("noteId");
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

        noteReferenceService = new NoteReferenceService(getContext());

        // Find note instance
        noteReferenceService.getAllArticleNotes(pageId, notes -> {
            if (notes != null) {
                for (Note n : notes) {
                    if (n.getId() == noteId) {
                        noteInstance = n;
                    }
                }
            }
        });

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

        // Trim text button
        ImageButton backspace = view.findViewById(R.id.icon_backspace);

        backspace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editBody.getSelectionStart() != editBody.getSelectionEnd()) {
                    int start = editBody.getSelectionStart();
                    int end = editBody.getSelectionEnd();

                    // Update view
                    note.replace(start, end, "");
                    editBody.setText(note);

                } else {
                    Toast.makeText(getContext(), "Select text to trim first", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Reset to original note button
        ImageButton resetButton = view.findViewById(R.id.icon_reset);

        resetButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // Reset text
                noteInstance.resetToOriginalText();

                // Update view
                note.replace(0, note.length(), noteInstance.getText());
                editBody.setText(note);
            }
        });

        // Done commit button
        ImageButton done = view.findViewById(R.id.icon_done);
        done.setOnClickListener(v -> {
            AlertDialog dialog = new AlertDialog.Builder(getContext())
                    .setTitle("Would you like to save changes?")
                    .setPositiveButton("Yes", (dialog12, which) -> {
                        String span = buildSpanKey(note).toString();

                        // Update Note in DB
                        noteInstance.updateText(note.toString());
                        noteInstance.setSpan(span);

                        Log.i("DEBUG", "NOTE EDITED AND SAVING");
                        noteReferenceService.updateNoteText(noteInstance, () -> Log.i("DEBUG", "NOTE EDITED AND SAVED"));

                        dialog12.dismiss();
                        getFragmentManager().popBackStackImmediate();
                    })
                    .setNegativeButton("No", (dialog1, which) -> {
                        dialog1.dismiss();
                        getFragmentManager().popBackStackImmediate();
                    })
                    .show();
        });
        return view;
    }

    @NonNull
    public static NotesEditFragment newInstance(String note, String spans, int noteId) {
        NotesEditFragment fragment = new NotesEditFragment();

        Bundle args = new Bundle();
        args.putString("note", note);
        args.putString("spans", spans);
        args.putInt("noteId", noteId);

        fragment.setArguments(args);
        return fragment;
    }

    public StringBuilder buildSpanKey(SpannableStringBuilder note) {
        StringBuilder saved = new StringBuilder();

        boolean isBold, isItalics, isUnderlined;
        int next;
        for (int i = 0; i < note.length(); i = next) {

            isBold = false;
            isItalics = false;
            isUnderlined = false;

            // find the next span transition
            next = note.nextSpanTransition(i, note.length(), CharacterStyle.class);
            int spanEnd = next;

            // get all spans in this range
            int numOfSpans = 0;
            StringBuilder spanTypes = new StringBuilder();
            CharacterStyle[] spans = note.getSpans(i, next, CharacterStyle.class);
            for (CharacterStyle span1 : spans) {
                if (span1 instanceof StyleSpan) {
                    StyleSpan span = (StyleSpan) span1;
                    if (span.getStyle() == 1) {
                        if (!isBold) {
                            spanTypes.append("b");
                            isBold = true;
                        } else {
                            numOfSpans--;
                        }
                    }
                    if (span.getStyle() == 2) {
                        if (!isItalics) {
                            spanTypes.append("i");
                            isItalics = true;
                        } else {
                            numOfSpans--;
                        }
                    }
                }
                if (span1 instanceof UnderlineSpan) {
                    if (!isUnderlined) {
                        spanTypes.append("u");
                        isUnderlined = true;
                    } else {
                        numOfSpans--;
                    }
                }
                numOfSpans++;
            }

            saved.
                    append("[").
                    append(i).
                    append(".").
                    append(spanEnd).
                    append(".").
                    append(numOfSpans).
                    append(spanTypes).
                    append("]");
        }
        Log.i("DEBUG SPANS", saved.toString());

        return saved;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
