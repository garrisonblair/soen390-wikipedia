package org.wikipedia.userstatistics;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.wikipedia.R;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

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

        setStatView(view);

        ImageButton achievementButton = view.findViewById(R.id.achievement_button);
        achievementButton.setOnClickListener(v -> {
            openAchievementPage();
        });
        return view;
    }

    private void setStatView(View view) {
        ArrayList<StatObject> stats = getStatList();
        ListView statList = view.findViewById(R.id.stat_list);
        CustomAdapter adapter = new CustomAdapter(getContext(), stats);
        statList.setAdapter(adapter);
    }

    private ArrayList getStatList() {
        ArrayList<StatObject> stats = new ArrayList<>();
        StatCalculator statCalculator = new StatCalculator(getContext());
        stats.add(new StatObject("Total Articles Read",
                        Integer.toString(
                                statCalculator.getArticleStats().getTotalArticlesRead())
                )
        );

        if (statCalculator.getArticleStats().getLongestReadArticleTitle() != null) {
            stats.add(new StatObject("Longest Read Article",
                            statCalculator.getArticleStats().getLongestReadArticleTitle()
                                    + ": You spent " + Long.toString(
                                    TimeUnit.MILLISECONDS.toMinutes(
                                            statCalculator.getArticleStats().getLongestReadArticleTime()
                                    )
                            ) + " minutes"
                    )
            );
        } else {
            stats.add(new StatObject("Longest Read Article", "No Article has been read yet"));
        }

        stats.add(new StatObject("Average Time Spent Reading",
                Long.toString(TimeUnit.MILLISECONDS.toMinutes(
                        statCalculator.getArticleStats().getAverageTimeSpentReading()))
                        + " minutes")
        );

        stats.add(new StatObject("Total Time Spent Reading",
                Long.toString(TimeUnit.MILLISECONDS.toMinutes(
                        statCalculator.getArticleStats().getTotalTimeSpentReading()))
                        + " minutes")
        );

        stats.add(new StatObject("Total Notes",
                        Integer.toString(statCalculator.getNoteStats().getTotalNotes())
                )
        );

        stats.add(new StatObject("Total Noted Articles",
                        Integer.toString(statCalculator.getNoteStats().getTotalNotedArticles())
                )
        );

        stats.add(new StatObject("Notes per Article Ratio",
                        Double.toString(statCalculator.getNoteStats().getNotesPerArticle())
                )
        );
        return stats;
    }

    private class StatObject {
        private String name;
        private String description;

        StatObject(String name, String description) {
            this.name = name;
            this.description = description;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }
    }

    private class CustomAdapter extends BaseAdapter {

        private LayoutInflater inflater;
        private ArrayList<StatObject> objects;

        private class ViewHolder {
            private TextView textViewTitle;
            private TextView textViewDescription;
            private ImageButton checkStatus;
        }

        CustomAdapter(Context context, ArrayList<StatObject> objects) {
            inflater = LayoutInflater.from(context);
            this.objects = objects;
        }

        public int getCount() {
            return objects.size();
        }

        public StatObject getItem(int position) {
            return objects.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            StatisticFragment.CustomAdapter.ViewHolder holder = null;
            if (convertView == null) {
                holder = new StatisticFragment.CustomAdapter.ViewHolder();
                convertView = inflater.inflate(R.layout.achievement_row, null);
                holder.textViewTitle = convertView.findViewById(R.id.achievementRowTitle);
                holder.textViewDescription = convertView.findViewById(R.id.achievementRowDescription);
                holder.checkStatus = convertView.findViewById(R.id.check_status);
                convertView.setTag(holder);
            } else {
                holder = (StatisticFragment.CustomAdapter.ViewHolder) convertView.getTag();
            }
            holder.textViewTitle.setText(objects.get(position).getName());
            holder.textViewDescription.setText(objects.get(position).getDescription());

            LinearLayout layout = convertView.findViewById(R.id.achievement_text);
            Drawable background =  layout.getBackground();
            background.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC);

            return convertView;
        }
    }

    private void openAchievementPage() {
        Log.d("DEV_DEBUG", "Should open achievement activity");
        Intent intent = new Intent(getContext(), AchievementActivity.class);
        startActivity(intent);
    }
}
