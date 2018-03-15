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
    private boolean speaking = false;

    private List<Note> notes;
    private List<Reference> references;

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
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes, container, false);

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

                //Listener for the share notes button
                ImageButton shareButton = view.findViewById(R.id.notes_share);
                shareButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (notes != null && notes.size() != 0) {
                            StringBuilder notesToShare = new StringBuilder();
                            Set<Reference> referencesToShare = new HashSet<>();
                            int indexNote = 0;
//                            String uriOrigin = UriUtil.getUrlWithProvenance(getContext(), pageTitle, R.string.prov_share_link);

                            notesToShare.append("Notes for Wikipedia article: " + title + "\n\n");
//                            notesToShare.append(uriOrigin + "\n\n");
                            for (Note noteItem:notes) {
                                indexNote++;
                                notesToShare.append("- Note " + indexNote + ":\n\n");
                                notesToShare.append(noteItem.getText() + "\n\n");
                                List<Reference> noteRefs = noteItem.getAllReferences();
                                if (noteRefs.size() > 0) {
                                    referencesToShare.addAll(noteRefs);
                                }
                            }

                            notesToShare.append("References:\n\n");
                            if (referencesToShare == null) {
                                notesToShare.append("None.");
                            } else {
                                for (Reference referenceItem : referencesToShare) {
                                    notesToShare.append("- " + referenceItem.getText() + "\n");
                                }
                            }
                            ShareUtil.shareText(getContext(), title, notesToShare.toString());
                        }
                    }
                });

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
                            Log.i("REFERENCE SIZE", Integer.toString(notes.get(position).getAllReferences().size()));
                            references = notes.get(position).getAllReferences();
                        }
                        ArrayList<String> refsText = new ArrayList();
                        String ref;
                        for (Reference reference : references) {
                            Log.i("REFERENCES", reference.getText());
                            ref = "[" + reference.getNumber() + "] " + reference.getText();
                            refsText.add(ref);
                        }

                        // Creating ListView for references
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
