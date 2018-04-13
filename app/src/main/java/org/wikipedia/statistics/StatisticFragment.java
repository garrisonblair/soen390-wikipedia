package org.wikipedia.statistics;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import org.wikipedia.R;
import org.wikipedia.userstatistics.StatCalculator;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.Unbinder;

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
        setTextViews(view);

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

    public ArrayList getStatisticList() {

        ArrayList<String> stats = new ArrayList();
        StatCalculator statCalculator = new StatCalculator(getContext());

        stats.add(String.valueOf(statCalculator.getArticleStats().getTotalArticlesRead()));

        if (statCalculator.getArticleStats().getLongestReadArticleTitle() == null) {
            stats.add("No article has been read yet.");
        } else {
            stats.add(statCalculator.getArticleStats().getLongestReadArticleTitle());
        }

        stats.add(statCalculator.getArticleStats().getAverageTimeSpentReading() + " mins");
        stats.add(statCalculator.getArticleStats().getTotalTimeSpentReading() + " mins");
        stats.add(String.valueOf(statCalculator.getNoteStats().getTotalNotedArticles()));
        stats.add(String.valueOf(statCalculator.getNoteStats().getTotalNotes()));
        stats.add(String.valueOf(statCalculator.getNoteStats().getNotesPerArticle()));

        return stats;
    }

    private void setTextViews(View view) {

        //avoid magic number error
        final int index0 = 0;
        final int index1 = 1;
        final int index2 = 2;
        final int index3 = 3;
        final int index4 = 4;
        final int index5 = 5;
        final int index6 = 6;
        ArrayList<String> statistics = getStatisticList();

        TextView textView = view.findViewById(R.id.totalReadArticles);
        textView.setText(statistics.get(index0));
        textView = view.findViewById(R.id.longestReadingArticle);
        textView.setText(statistics.get(index1));
        textView = view.findViewById(R.id.averageTimeSpend);
        textView.setText(statistics.get(index2));
        textView = view.findViewById(R.id.totalTimeSpend);
        textView.setText(statistics.get(index3));
        textView = view.findViewById(R.id.totalNotedArticles);
        textView.setText(statistics.get(index4));
        textView = view.findViewById(R.id.totalNotes);
        textView.setText(statistics.get(index5));
        textView = view.findViewById(R.id.ratio);
        textView.setText(statistics.get(index6));
    }
}
