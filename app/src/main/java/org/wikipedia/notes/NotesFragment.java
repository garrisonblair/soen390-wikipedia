package org.wikipedia.notes;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.wikipedia.R;

import java.util.ArrayList;

public class NotesFragment extends Fragment {

    private String title;
    private String pageId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        if (getArguments() != null) {
//            title = getArguments().getString("pageTitle");
//        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes, container, false);

        ArrayList<String> notes = new ArrayList();

//        notes.add(title);
        notes.add("Material Design Icons' growing icon collection allows designers and developers targeting various platforms to download icons in the format, color and size they need for any project.");
        notes.add("The app is primarily being developed by the Wikimedia Foundation's Mobile Apps team. This README provides high-level guidelines for getting started with the project.");


//        ArrayList<String> notes = getArguments().getStringArrayList("notes");
        ListView noteList = view.findViewById(R.id.notes_list);

        noteList.setAdapter(new ArrayAdapter<String>(getContext(), R.layout.simple_row, notes));
        Log.i("GARRISON", "WAS HERE");
        return view;
    }
//    @NonNull
//    public static NotesFragment newInstance(@NonNull SearchInvokeSource source,
//                                             @Nullable String query) {
//        NotesFragment fragment = new NotesFragment();
//
//        Bundle args = new Bundle();
//
//        fragment.setArguments(args);
//        return fragment;
//    }
}
