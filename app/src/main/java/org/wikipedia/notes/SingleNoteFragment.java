package org.wikipedia.notes;

import android.graphics.Color;
import android.os.Bundle;
import android.speech.tts.UtteranceProgressListener;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import org.wikipedia.R;
import org.wikipedia.notebook.NoteReferenceService;
import org.wikipedia.texttospeech.TTSWrapper;

import java.util.ArrayList;


public class SingleNoteFragment extends Fragment {

    private SpannableStringBuilder note;
    private String title;
    private String noteSpans;
    private int pageId;
    private int noteId;
    private int position;
    private ArrayList<String> references;
    private NoteReferenceService noteReferenceService;
    private TTSWrapper tts;
    private boolean speaking = false;
    private View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String noteText = "";

        if (getActivity().getIntent().getExtras() != null) {
            Bundle bundleRead = getActivity().getIntent().getExtras();
            title = bundleRead.getString("pageTitle");
            pageId = bundleRead.getInt("pageId");
        }

        if (getArguments() != null) {
            noteText = getArguments().getString("note");
            position = getArguments().getInt("position");
            references = getArguments().getStringArrayList("references");
            noteSpans = getArguments().getString("spans");
            noteId = getArguments().getInt("noteId");
        }

        tts = TTSWrapper.getInstance(getContext(), new UtteranceProgressListener() {
            @Override
            public void onStart(String utteranceId) { }
            @Override
            public void onDone(String utteranceId) { }
            @Override
            public void onError(String utteranceId) { }
        });

        note = ((NotesActivity)getActivity()).annotate(noteText, noteSpans);
    }

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        view = inflater.inflate(R.layout.single_note_fragment, container, false);

        // TextView for Title
        TextView noteTitle = view.findViewById(R.id.single_note_title);
        noteTitle.setText(title);

        // TextView for Body
        TextView noteBody = view.findViewById(R.id.single_note_body);
        noteBody.setText(note);

        // Button for text-to-speech of the note
        ImageButton speak = view.findViewById(R.id.note_speak);
        speak.setOnClickListener(v -> {
            int colorId = speak.getSolidColor();
            if (speaking) {
                tts.stop();
                speaking = false;
                speak.setColorFilter(colorId);
            } else {
                tts.speak(note.toString());
                speaking = true;
                speak.setColorFilter(Color.BLUE);
            }
        });

        // Button for commenting on the note
        // TODO: implement action for adding comment

        // Button for editing the note
        ImageButton edit = view.findViewById(R.id.note_edit);
        edit.setOnClickListener(v -> openNotesEditFragment(note.toString(), noteSpans, noteId));

        // Button for deleting of the note
        ImageButton deleteNote = view.findViewById(R.id.note_delete);
        deleteNote.setOnClickListener(v -> {
            NotesFragment fragment = (NotesFragment) getFragmentManager().findFragmentById(R.id.fragment_notes);
            fragment.deleteNote(position);
        });

        ImageButton back = view.findViewById(R.id.single_note_back_button);
        back.setOnClickListener(v -> getFragmentManager().popBackStackImmediate());

        // Creating ListView for references
        ListView dialogRefs = view.findViewById(R.id.reference_list);

//        dialogRefs.setOnTouchListener(swipeListener);

        dialogRefs.setAdapter(new ArrayAdapter<String>(getContext(), R.layout.reference_row, references));

        return view;
    }

    @NonNull
    public static SingleNoteFragment newInstance(String note, String spans, ArrayList<String> references, int position, int noteId) {
        SingleNoteFragment fragment = new SingleNoteFragment();

        Bundle args = new Bundle();
        args.putString("note", note);
        args.putString("spans", spans);
        args.putStringArrayList("references", references);
        args.putInt("position", position);
        args.putInt("noteId", noteId);

        fragment.setArguments(args);
        return fragment;
    }

    private void openNotesEditFragment(String note, String spans, int noteId) {
        NotesEditFragment fragment = notesEditFragment();

        if (fragment == null) {
            fragment = NotesEditFragment.newInstance(note, spans, noteId);
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.activity_note_container, fragment)
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Nullable
    private NotesEditFragment notesEditFragment() {

        return (NotesEditFragment) getActivity().getSupportFragmentManager()
                .findFragmentById(R.id.fragment_notes_edit);
    }

    @Override
    public void onPause() {
        tts.shutdown();
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        tts = TTSWrapper.getInstance(getContext(), new UtteranceProgressListener() {
            @Override
            public void onStart(String utteranceId) { }
            @Override
            public void onDone(String utteranceId) { }
            @Override
            public void onError(String utteranceId) { }
        });
    }

    @Override
    public void onDestroyView() {
        tts.shutdown();
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        tts.shutdown();
    }
}
