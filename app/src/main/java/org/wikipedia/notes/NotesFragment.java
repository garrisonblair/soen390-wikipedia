package org.wikipedia.notes;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import org.wikipedia.R;
import org.wikipedia.notebook.Reference;
import org.wikipedia.texttospeech.TTSWrapper;

import java.util.ArrayList;
import java.util.List;

public class NotesFragment extends Fragment {

    private String title;
    private String pageId;
    private TTSWrapper tts;
    private boolean speaking = false;

    private List<Note> notes;
    private List<Reference> refs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getActivity().getIntent().getExtras() != null) {
            Bundle bundleRead = getActivity().getIntent().getExtras();
            title = bundleRead.getString("pageTitle");
            pageId = bundleRead.getString("pageId");
        }

        tts = TTSWrapper.getInstance(getContext(), new UtteranceProgressListener() {
            @Override
            public void onStart(String utteranceId) {}
            @Override
            public void onDone(String utteranceId) {}
            @Override
            public void onError(String utteranceId) {}
        });
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes, container, false);

        // FOR TESTING PURPOSES ONLY
        // Will be replaced with list of notes that will be loaded from internal db
//        ArrayList<String> notes = new ArrayList();
//        notes.add("Material Design Icons' growing icon collection allows designers and developers targeting various platforms to download icons in the format, color and size they need for any project.");
//        notes.add("The app is primarily being developed by the Wikimedia Foundation's Mobile Apps team. This README provides high-level guidelines for getting started with the project.");

        ArrayList<String> notesText = new ArrayList();
        for (Note note : notes) {
            notesText.add(note.getText);
        }

//        ArrayList<String> refs = new ArrayList();
//        refs.add("Reference 1");
//        refs.add("Reference 2");

        // Setting Title in the TextView
        TextView titleView = view.findViewById(R.id.note_title);
        titleView.setText(title);
        // Creating the ListView of notes
        ListView noteList = view.findViewById(R.id.notes_list);
        noteList.setAdapter(new ArrayAdapter<String>(getContext(), R.layout.simple_row, notesText));

        // Setting listener to the items in the ListView to open individual notes in dialog
        noteList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.single_note_dialog);

                TextView dialogTitle = dialog.findViewById(R.id.note_dialog_title);
                dialogTitle.setText(title);

                TextView dialogBody = dialog.findViewById(R.id.note_dialog_body);
                dialogBody.setText(notesText.get(position));

                // BUTTON FOR TEXT TO SPEECH TO BE IMPLEMENTED
                ImageButton speak = dialog.findViewById(R.id.note_speak);
                speak.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int colorId = speak.getSolidColor();
                        if (speaking) {
                            tts.stop();
                            speaking = false;
                            speak.setColorFilter(colorId);
                        }
                        else {
                            tts.speak(notesText.get(position));
                            speaking = true;
                            speak.setColorFilter(Color.BLUE);
                        }
                    }
                });

                references = notes.get(position).getAllReferences();
                ArrayList<String> refsText;
                String ref;
                for (Reference reference : references) {
                    ref = "[" + reference.getNumber() + "] " + reference.getText();
                    refsText.add(ref);
                }

                ListView dialogRefs = dialog.findViewById(R.id.reference_list);
                dialogRefs.setAdapter(new ArrayAdapter<String>(getContext(), R.layout.reference_row, refsText));

                dialog.show();

                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        tts.stop();
                    }
                });
            }
        });

        return view;
    }

    @NonNull
    public static NotesFragment newInstance(String title, String pageId) {
        NotesFragment fragment = new NotesFragment();

        Bundle args = new Bundle();
        args.putString("pageId", pageId);
        args.putString("pageTitle", title);

        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    public static NotesFragment newInstance() {
        NotesFragment fragment = new NotesFragment();
        return fragment;
    }
}
