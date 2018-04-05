package org.wikipedia.statistics;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.wikipedia.R;
import org.wikipedia.views.SwipeableItemTouchHelperCallback;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by KammyWong on 2018-04-05.
 */

public class StatisticFragment extends Fragment {

    private Unbinder unbinder;

    @NonNull
    public static StatisticFragment newInstance() {
        return new StatisticFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);
        unbinder = ButterKnife.bind(this, view);

        ItemTouchHelper.Callback touchCallback = new SwipeableItemTouchHelperCallback(getContext());
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(touchCallback);

        return view;
    }
}
