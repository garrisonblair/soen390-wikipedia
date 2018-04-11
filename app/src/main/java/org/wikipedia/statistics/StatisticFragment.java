package org.wikipedia.statistics;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.wikipedia.R;
import org.wikipedia.userstatistics.StatCalculator;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.Unbinder;


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

        stats = new ArrayList();
        StatCalculator statCalculator = new StatCalculator(getContext());
        stats.add("Totad read articles: " + statCalculator.getArticleStats().getTotalArticlesRead());
        if (statCalculator.getArticleStats().getLongestReadArticleTitle() == null) {
            stats.add("Longest reading article: No article has read yet.");
        } else {
            stats.add("Longest reading article: " + statCalculator.getArticleStats().getLongestReadArticleTitle());
        }
        stats.add("Average time spend on reading: " + statCalculator.getArticleStats().getAverageTimeSpentReading() + " mins");
        stats.add("Total time spend on reading: " + statCalculator.getArticleStats().getTotalTimeSpentReading() + " mins");
        stats.add("Total noted articles: " + statCalculator.getNoteStats().getTotalNotedArticles());
        stats.add("Total notes: " + statCalculator.getNoteStats().getTotalNotes());
        stats.add("Ratio notes/articles: " + statCalculator.getNoteStats().getNotesPerArticle());

        ListView statList = view.findViewById(R.id.statistic_list);
        statList.setAdapter(new ArrayAdapter<String>(getContext(), R.layout.simple_row, stats));

        ImageButton achievementButton = view.findViewById(R.id.achievement_button);

        achievementButton.setOnClickListener(v -> {
            openAchievementPage();
        });

        return view;

    }

    private void openAchievementPage() {
        Log.d("DEV_DEBUG", "Should open achievement activity");
        Intent intent = new Intent(getContext(), AchievementActivity.class);
        startActivity(intent);
    }
}
