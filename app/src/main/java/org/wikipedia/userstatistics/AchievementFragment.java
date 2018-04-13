package org.wikipedia.userstatistics;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.wikipedia.R;
import org.wikipedia.userstatistics.Database.AchievementEntity;

import java.util.ArrayList;

public class AchievementFragment extends Fragment {

    private class CustomAdapter extends BaseAdapter {

        private LayoutInflater inflater;
        private ArrayList<AchievementEntity> objects;

        private static final int R_GOLD_COLOR = 255;
        private static final int G_GOLD_COLOR = 223;
        private static final int B_GOLD_COLOR = 0;

        private static final int R_GREY_COLOR = 46;
        private static final int G_GREY_COLOR = 49;
        private static final int B_GREY_COLOR = 54;

        private class ViewHolder {
            private TextView textViewTitle;
            private TextView textViewDescription;
            private ImageButton checkStatus;
        }

        CustomAdapter(Context context, ArrayList<AchievementEntity> objects) {
            inflater = LayoutInflater.from(context);
            this.objects = objects;
        }

        public int getCount() {
            return objects.size();
        }

        public AchievementEntity getItem(int position) {
            return objects.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.achievement_row, null);
                holder.textViewTitle = convertView.findViewById(R.id.achievementRowTitle);
                holder.textViewDescription = convertView.findViewById(R.id.achievementRowDescription);
                holder.checkStatus = convertView.findViewById(R.id.check_status);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.textViewTitle.setText(objects.get(position).getName());
            holder.textViewDescription.setText(objects.get(position).getDescription());

            if (objects.get(position).getObtained() == 1 && objects.get(position).getChecked() == 0) {
                holder.checkStatus.setVisibility(View.VISIBLE);
                ViewHolder finalHolder = holder;
                holder.checkStatus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v){
                        finalHolder.checkStatus.setVisibility(View.INVISIBLE);
                    }
                });
            }

            if (objects.get(position).getObtained() == 1) {
                LinearLayout layout = convertView.findViewById(R.id.achievement_text);
                Drawable background =  layout.getBackground();
                background.setColorFilter(Color.rgb(R_GOLD_COLOR, G_GOLD_COLOR, B_GOLD_COLOR), PorterDuff.Mode.SRC);
            } else {
                LinearLayout layout = convertView.findViewById(R.id.achievement_text);
                Drawable background =  layout.getBackground();
                background.setColorFilter(Color.rgb(R_GREY_COLOR, G_GREY_COLOR, B_GREY_COLOR), PorterDuff.Mode.SRC);
            }
            return convertView;
        }
    }

    public static AchievementFragment newInstance() {
        return new AchievementFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_achievement, container, false);

        ArrayList<AchievementEntity> achievements = new ArrayList();

        AchievementService service = new AchievementService(getContext());
        service.getAllAchievements(achievements1 -> {
            for (AchievementEntity achievementEntity: achievements1) {
                achievements.add(achievementEntity);
            }
        });

        ListView achievementList = view.findViewById(R.id.achievement_list);

        CustomAdapter customAdapter = new CustomAdapter(getContext(), achievements);

        achievementList.setAdapter(customAdapter);

        service.checkAchievements(() -> {

        });

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
