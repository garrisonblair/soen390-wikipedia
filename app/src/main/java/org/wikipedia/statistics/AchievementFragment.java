package org.wikipedia.statistics;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;
import org.wikipedia.R;
import org.wikipedia.userstatistics.AchievementChecker;
import org.wikipedia.userstatistics.AchievementService;
import org.wikipedia.userstatistics.Database.AchievementEntity;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static cn.pedant.SweetAlert.SweetAlertDialog.*;


public class AchievementFragment extends Fragment {

    private class CustomAdapter extends BaseAdapter {

        private LayoutInflater inflater;
        private ArrayList<AchievementEntity> objects;

        private class ViewHolder {
            TextView textViewTitle;
            TextView textViewDescription;
        }

        public CustomAdapter(Context context, ArrayList<AchievementEntity> objects) {
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
            if(convertView == null) {
                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.achievement_row, null);
                holder.textViewTitle = (TextView) convertView.findViewById(R.id.achievementRowTitle);
                holder.textViewDescription = (TextView) convertView.findViewById(R.id.achievementRowDescription);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.textViewTitle.setText(objects.get(position).getName());
            holder.textViewDescription.setText(objects.get(position).getDescription());
            return convertView;
        }
    }

    private static final int SECRET_CLICK_LIMIT = 3;
    private int mSecretClickCount;

    public static AchievementFragment newInstance() {
        AchievementFragment fragment = new AchievementFragment();
        return fragment;
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
        service.getAllAchievements((AchievementService.GetAllAchievementsCallback) achievements1 -> {
            for (AchievementEntity achievementEntity: achievements1) {
                achievements.add(achievementEntity);
            }
        });

        ListView achievementList = view.findViewById(R.id.achievement_list);

        CustomAdapter customAdapter = new CustomAdapter(getContext(), achievements);

        achievementList.setAdapter(customAdapter);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSecretClickCount++;

                if(mSecretClickCount == SECRET_CLICK_LIMIT) {
//                    SweetAlertDialog dialog = new SweetAlertDialog(getContext(), SUCCESS_TYPE);
//                    dialog.setTitleText("Good job!");
//                    dialog.setConfirmText("See Achievements");
//                    dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                        @Override
//                        public void onClick(SweetAlertDialog sDialog) {
//                            sDialog.dismissWithAnimation();
//
//                            // Open achievements activity
//                            Intent intent = new Intent(getContext(), AchievementActivity.class);
//                            startActivity(intent);
//                        }
//                    });
//                    dialog.setCancelButton("Close", new SweetAlertDialog.OnSweetClickListener() {
//                        @Override
//                        public void onClick(SweetAlertDialog sDialog) {
//                            sDialog.dismissWithAnimation();
//                        }
//                    });
//                    dialog.show();
                    AchievementChecker checker = new AchievementChecker("Wikinerd", 1, getContext());

                    mSecretClickCount = 0;
                }
            }
        });
    }
}
