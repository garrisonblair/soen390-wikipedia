package org.wikipedia.notes;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import org.wikipedia.R;
import org.wikipedia.notebook.NoteReferenceService;


public class NotesCommentFragment extends Fragment {

    private String commentText;
    private int noteId;
    private int pageId;
    private View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getActivity().getIntent() != null) {
            if (getActivity().getIntent().getExtras() != null) {
                Bundle bundleRead = getActivity().getIntent().getExtras();
            }
        }
        if (getArguments() != null) {
            commentText = getArguments().getString("comment");
            noteId = getArguments().getInt("noteId");
            pageId = getArguments().getInt("pageId");
        }
    }

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        view = inflater.inflate(R.layout.fragment_notes_comment, container, false);

        EditText editBody = view.findViewById(R.id.notes_comment_body);
        editBody.setText(commentText);

        //Back button
        ImageButton back = view.findViewById(R.id.notes_comment_back_button);
        back.setOnClickListener(v -> getFragmentManager().popBackStackImmediate());

        // Save changes
        ImageButton doneBtn = view.findViewById(R.id.notes_comment_done_button);
        doneBtn.setOnClickListener(v -> {
            String comment = editBody.getText().toString();

            NoteReferenceService service = new NoteReferenceService((getContext()));
            service.updateCommentOnNote(comment, noteId, new NoteReferenceService.SetCommentCallBack() {
                @Override
                public void afterSetComment() {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                            if (imm != null) {
                                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                            }
                            Toast.makeText(getContext(), "Comment successfully saved", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });

        });

        return view;
    }

    @NonNull
    public static NotesCommentFragment newInstance(String comment, int noteId, int pageId) {
        NotesCommentFragment fragment = new NotesCommentFragment();

        Bundle args = new Bundle();
        args.putString("comment", comment);
        args.putInt("noteId", noteId);
        args.putInt("pageId", pageId);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
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
