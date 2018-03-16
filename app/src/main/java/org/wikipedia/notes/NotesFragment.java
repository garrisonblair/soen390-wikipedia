package org.wikipedia.notes;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.tts.UtteranceProgressListener;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import org.wikipedia.R;
import org.wikipedia.notebook.Note;
import org.wikipedia.notebook.NoteReferenceService;
import org.wikipedia.notebook.Reference;
import org.wikipedia.page.listeners.OnSwipeTouchListener;
import org.wikipedia.texttospeech.TTSWrapper;

import java.util.ArrayList;
import java.util.List;

public class NotesFragment extends Fragment {

    private String title;
    private int pageId;
    private TTSWrapper tts;
    private boolean speaking = false;

    private List<Reference> references;

    private View view;
    private View rootView;

    private View.OnTouchListener swipeListener;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getActivity().getIntent().getExtras() != null) {
            Bundle bundleRead = getActivity().getIntent().getExtras();
            title = bundleRead.getString("pageTitle");
            pageId = bundleRead.getInt("pageId");
        }

        tts = TTSWrapper.getInstance(getContext(), new UtteranceProgressListener() {
            @Override
            public void onStart(String utteranceId) { }
            @Override
            public void onDone(String utteranceId) { }
            @Override
            public void onError(String utteranceId) { }
        });

        swipeListener = new OnSwipeTouchListener(getContext()){
            public void onSwipeRight() {
                Log.d("DEV_DEBUG", "Swipe Right");
                Log.d("DEV_DEBUG", "Should close notes activity and go back to article");
                getActivity().onBackPressed();
            }
            public void onSwipeLeft() {
                Log.d("DEV_DEBUG", "Swipe Left");
            }
            public void onSwipeTop() {
                Log.d("DEV_DEBUG", "Swipe Up");
            }
            public void onSwipeBottom() {
                Log.d("DEV_DEBUG", "Swipe Down");
            }
        };

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //View view = inflater.inflate(R.layout.fragment_notes, container, false);
        view = inflater.inflate(R.layout.fragment_notes, container, false);
        rootView = view.getRootView();
        rootView.setOnTouchListener(swipeListener);

        NoteReferenceService service = new NoteReferenceService(getContext());
        service.getAllArticleNotes(pageId, new NoteReferenceService.GetNotesCallback() {

            @Override
            public void afterGetNotes(List<Note> notes) {

                // Creating ArrayList with text notes
                ArrayList<String> notesText = new ArrayList();

                if (notes != null) {
                    for (Note note: notes) {
                        notesText.add(note.getText());
                    }
                }

                // Setting Title in the TextView
                TextView titleView = view.findViewById(R.id.note_title);
                titleView.setText(title);
                // Creating the ListView of notes
                ListView noteList = view.findViewById(R.id.notes_list);
                noteList.setAdapter(new ArrayAdapter<String>(getContext(), R.layout.simple_row, notesText));

                noteList.setOnTouchListener(swipeListener);

                // Setting listener to the items in the ListView to open individual notes in dialog
                noteList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        final Dialog dialog = new Dialog(getContext());
                        dialog.setContentView(R.layout.single_note_dialog);

                        // TextView for Title
                        TextView dialogTitle = dialog.findViewById(R.id.note_dialog_title);
                        dialogTitle.setText(title);

                        // TextView for Body
                        TextView dialogBody = dialog.findViewById(R.id.note_dialog_body);
                        dialogBody.setText(notesText.get(position));

                        // Button for text-to-speech of the note
                        ImageButton speak = dialog.findViewById(R.id.note_speak);
                        speak.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int colorId = speak.getSolidColor();
                                if (speaking) {
                                    tts.stop();
                                    speaking = false;
                                    speak.setColorFilter(colorId);
                                } else {
                                    tts.speak(notesText.get(position));
                                    speaking = true;
                                    speak.setColorFilter(Color.BLUE);
                                }
                            }
                        });

                        // Getting the references for the selected note and creating strings with them
                        if (notes.get(position).getAllReferences() != null) {
                            references = notes.get(position).getAllReferences();
                        }
                        ArrayList<String> refsText = new ArrayList();
                        String ref;
                        for (Reference reference : references) {
                            ref = "[" + reference.getNumber() + "] " + reference.getText();
                            refsText.add(ref);
                        }

                        // Creating ListView for references
                        ListView dialogRefs = dialog.findViewById(R.id.reference_list);

                        dialogRefs.setOnTouchListener(swipeListener);

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
