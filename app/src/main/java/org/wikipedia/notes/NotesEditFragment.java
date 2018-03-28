package org.wikipedia.notes;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
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
    private int pageId;
    private int noteId;
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

        // Get data passed from SingleNoteFragment
        if (getArguments() != null) {
            note = new SpannableStringBuilder(getArguments().getString("note"));
            noteId = getArguments().getInt("noteId");
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

        // Setting text to bold button
        ImageButton bold = view.findViewById(R.id.icon_bold);
        bold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editBody.getSelectionStart() != editBody.getSelectionEnd()) {
                    int start = editBody.getSelectionStart();
                    int end = editBody.getSelectionEnd();
                    note.setSpan(new StyleSpan(Typeface.BOLD), start, end, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
                    editBody.setText(note);
                } else {
                    Toast.makeText(getContext(), "Select text to make bold first", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Setting text to italics button
        ImageButton italics = view.findViewById(R.id.icon_italics);
        italics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editBody.getSelectionStart() != editBody.getSelectionEnd()) {
                    int start = editBody.getSelectionStart();
                    int end = editBody.getSelectionEnd();
                    note.setSpan(new StyleSpan(Typeface.ITALIC), start, end, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
                    editBody.setText(note);
                } else {
                    Toast.makeText(getContext(), "Select text to italicize first", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Setting text to underlined button
        ImageButton underline = view.findViewById(R.id.icon_underline);
        underline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editBody.getSelectionStart() != editBody.getSelectionEnd()) {
                    int start = editBody.getSelectionStart();
                    int end = editBody.getSelectionEnd();
                    note.setSpan(new UnderlineSpan(), start, end, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
                    editBody.setText(note);
                } else {
                    Toast.makeText(getContext(), "Select text to underline first", Toast.LENGTH_SHORT).show();
                }
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

                    note.replace(start, end, "");

                    NoteReferenceService service = new NoteReferenceService(getContext());
                    service.getAllArticleNotes(pageId, new NoteReferenceService.GetNotesCallback() {

                        @Override
                        public void afterGetNotes(List<Note> notes) {
                            if (notes != null) {
                                // Find note instance
                                for (Note noteInstance : notes) {
                                    if (noteInstance.getId() == noteId) {

                                        // Update Note in DB
                                        noteInstance.updateText(note.toString());

                                        service.updateNoteText(noteInstance, new NoteReferenceService.UpdateNoteTextCallBack() {
                                            @Override
                                            public void afterUpdateNoteText() {
                                                getActivity().runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {

                                                        // Update edit body
                                                        editBody.setText(note);
                                                        Toast.makeText(getContext(), "Note successfully updated", Toast.LENGTH_SHORT).show();;
                                                    }
                                                });
                                            }
                                        });
                                    }
                                }
                            }
                        }
                    });
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
                NoteReferenceService service = new NoteReferenceService(getContext());

                service.getAllArticleNotes(pageId, new NoteReferenceService.GetNotesCallback() {

                    @Override
                    public void afterGetNotes(List<Note> notes) {
                        if (notes != null) {
                            // Find note instance
                            for (Note noteInstance : notes) {
                                if (noteInstance.getId() == noteId) {

                                    // Reset Note in DB
                                    noteInstance.resetToOriginalText();

                                    note.replace(0, note.length(), noteInstance.getText());

                                    service.updateNoteText(noteInstance, new NoteReferenceService.UpdateNoteTextCallBack() {
                                        @Override
                                        public void afterUpdateNoteText() {
                                            getActivity().runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    // Update edit body
                                                    editBody.setText(note);
                                                    Toast.makeText(getContext(), "Note successfully resetted", Toast.LENGTH_SHORT).show();;
                                                }
                                            });
                                        }
                                    });
                                }
                            }
                        }
                    }
                });
            }
        });

        return view;
    }

    @NonNull
    public static NotesEditFragment newInstance(String note, int noteId) {
        NotesEditFragment fragment = new NotesEditFragment();

        Bundle args = new Bundle();
        args.putString("note", note);
        args.putInt("noteId", noteId);

        fragment.setArguments(args);
        return fragment;
    }
}
