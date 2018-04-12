package org.wikipedia.userstatistics;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.wikipedia.R;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

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
        stats.add("Total articles read: " + statCalculator.getArticleStats().getTotalArticlesRead());
        if (statCalculator.getArticleStats().getLongestReadArticleTitle() == null) {
            stats.add("Longest read article: No article has been read yet.");
        } else {
            String title = statCalculator.getArticleStats().getLongestReadArticleTitle().replaceAll("_", " ");
            stats.add("Longest read article: " + title);
        }
        stats.add("Average time spend on reading: "
                + TimeUnit.MILLISECONDS.toMinutes(
                        statCalculator.getArticleStats().getAverageTimeSpentReading())
                + " minutes");
        stats.add("Total time spend reading: "
                + TimeUnit.MILLISECONDS.toMinutes(
                        statCalculator.getArticleStats().getTotalTimeSpentReading())
                + " minutes");
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
