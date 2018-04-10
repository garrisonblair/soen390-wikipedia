package org.wikipedia.statistics;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.wikipedia.R;
import org.wikipedia.userstatistics.StatCalculator;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by KammyWong on 2018-04-05.
 */

public class StatisticFragment extends Fragment {

    private Unbinder unbinder;
    private ArrayList<String> stats;

    @NonNull
    public static StatisticFragment newInstance() {
        return new StatisticFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);
        unbinder = ButterKnife.bind(this, view);

        //ItemTouchHelper.Callback touchCallback = new SwipeableItemTouchHelperCallback(getContext());
        //ItemTouchHelper itemTouchHelper = new ItemTouchHelper(touchCallback);

        stats = new ArrayList();
        StatCalculator statCalculator = new StatCalculator(getContext());
        stats.add("Totad read articles: " + statCalculator.getArticleStats().getTotalArticlesRead());
        stats.add("Total noted articles: " + statCalculator.getNoteStats().getTotalNotedArticles());
        stats.add("Total notes: " + statCalculator.getNoteStats().getTotalNotes());
        stats.add("Ratio notes/articles: " + statCalculator.getNoteStats().getNotesPerArticle());

        ListView statList = view.findViewById(R.id.statistic_list);
        statList.setAdapter(new ArrayAdapter<String>(getContext(), R.layout.simple_row, stats));

        return view;
    }
}
