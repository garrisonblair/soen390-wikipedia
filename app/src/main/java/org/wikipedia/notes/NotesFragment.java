package org.wikipedia.notes;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.tts.UtteranceProgressListener;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.wikipedia.R;
import org.wikipedia.notebook.Note;
import org.wikipedia.notebook.NoteReferenceService;
import org.wikipedia.notebook.Reference;
import org.wikipedia.page.listeners.OnSwipeTouchListener;
import org.wikipedia.texttospeech.TTSWrapper;
import org.wikipedia.util.ShareUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NotesFragment extends Fragment {

    private String title;
    private int pageId;
    private TTSWrapper tts;

    private NoteReferenceService noteReferenceService;

    private List<Note> notesList;
    private ArrayList<String> notesText;
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
                notesList = notes;
                // Creating ArrayList with text notes
                notesText = new ArrayList();
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

                //Listener for the share notes button
                ImageButton shareButton = view.findViewById(R.id.notes_share_button);
                shareButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (notes != null && notes.size() != 0) {
                            StringBuilder notesToShare = new StringBuilder();
                            Set<Reference> referencesToShare = new HashSet<>();
                            int indexNote = 0;

                            notesToShare.append("Notes for Wikipedia article: " + title);
                            for (Note noteItem:notes) {
                                indexNote++;
                                notesToShare.append("\n\n- Note " + indexNote + ":\n\n");
                                notesToShare.append(noteItem.getText() + "\n\n");
                                List<Reference> noteRefs = noteItem.getAllReferences();
                                if (noteRefs.size() > 0) {
                                    for (Reference ref : noteRefs) {
                                        notesToShare.append("[" + ref.getNumber() + "] ");
                                        referencesToShare.add(ref);
                                    }
                                }
                            }

                            notesToShare.append("\n\nReferences:\n\n");
                            if (referencesToShare == null || referencesToShare.size() == 0) {
                                notesToShare.append("None.");
                            } else {
                                for (Reference referenceItem : referencesToShare) {
                                    notesToShare.append("[" + referenceItem.getNumber() + "] " + referenceItem.getText() + "\n");
                                }
                            }
                            ShareUtil.shareText(getContext(), title, notesToShare.toString());
                        }
                    }
                });
                
                noteList.setOnTouchListener(swipeListener);
                // Button for text-to-speech of the note
                ImageButton backButton = view.findViewById(R.id.notes_back_button);
                backButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("DEV_DEBUG", "Back button pressed");
                        getActivity().onBackPressed();
                    }
                });

                // Setting listener to the items in the ListView to open individual notes in dialog
                noteList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

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

                        openSingleNoteFragment(notesText.get(position), refsText, position);
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
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void openSingleNoteFragment(String note, ArrayList<String> references, int position) {
        SingleNoteFragment fragment = singleNoteFragment();

        if (fragment == null) {
            fragment = SingleNoteFragment.newInstance(note, references, position);
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.activity_note_container, fragment)
                    .commit();
        }
    }

    @Nullable
    private SingleNoteFragment singleNoteFragment() {
        return (SingleNoteFragment) getActivity().getSupportFragmentManager()
                .findFragmentById(R.id.fragment_single_note);
    }

    public void deleteNote(int position) {
        Note test = notesList.get(position);
//        Dialog currentNoteDialog = dialog;

        new AlertDialog.Builder(getContext())
                .setTitle("Delete Note")
                .setMessage("Do you really want to delete this note?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        Note noteToDelete = notesList.get(position);
                        notesText.remove(position);
                        notesList.remove(position);
                        noteReferenceService = new NoteReferenceService(getContext().getApplicationContext());
                        noteReferenceService.deleteNote(noteToDelete, new NoteReferenceService.DeleteNoteCallBack() {
                            @Override
                            public void afterDeleteNote() {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        ListView noteList = view.findViewById(R.id.notes_list);
                                        ((BaseAdapter)noteList.getAdapter()).notifyDataSetChanged();
//                                        currentNoteDialog.dismiss();
                                    }
                                });
                            }
                        });
                        Toast.makeText(getContext(), "Note successfully deleted", Toast.LENGTH_SHORT).show();
                    }})
                .setNegativeButton(android.R.string.no, null).show();
    }
}
