package org.wikipedia.notes;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.wikipedia.R;
import org.wikipedia.search.SearchFragment;
import org.wikipedia.search.SearchInvokeSource;

public class NotesFragment extends Fragment {

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes, container, false);
        return view;
    }

    @NonNull
    public static NotesFragment newInstance(@NonNull SearchInvokeSource source,
                                             @Nullable String query) {
        NotesFragment fragment = new NotesFragment();

        Bundle args = new Bundle();
//        args.putInt(ARG_INVOKE_SOURCE, source.code());
//        args.putString(ARG_QUERY, query);

        fragment.setArguments(args);
        return fragment;
    }
}
