package org.wikipedia.statistics;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.wikipedia.R;
import org.wikipedia.userstatistics.AchievementService;
import org.wikipedia.userstatistics.Database.AchievementEntity;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static cn.pedant.SweetAlert.SweetAlertDialog.*;


public class AchievementFragment extends Fragment {

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

        ArrayList<String> achievements = new ArrayList();
        ArrayList<String> unlockedAchievements = new ArrayList();
        ArrayList<String> lockedAchievements = new ArrayList();

        AchievementService service = new AchievementService(getContext());
        service.getAllAchievements((AchievementService.GetAllAchievementsCallback) achievements1 -> {
            for (AchievementEntity ach: achievements1) {
                achievements.add(ach.getName());

                if (ach.getChecked() == 1) {
                    unlockedAchievements.add(ach.getName());
                } else {
                    lockedAchievements.add(ach.getName());
                }
            }
        });

        ListView achievementList = view.findViewById(R.id.achievement_list);

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), R.layout.simple_row, achievements){
            @Override
            public View getView(int position, View convertView, ViewGroup parent){

                // Get the current item from ListView
                View view = super.getView(position, convertView, parent);

                if(position %2 == 1)
                {
                    TextView tv = (TextView) view;
                    tv.setTextColor(Color.GREEN);
//                    tv.setPadding(0, 0, 0, 0);
                } else {
                    TextView tv = (TextView) view;
                    tv.setTextColor(Color.RED);
//                    tv.setPadding(0, 0, 0, 0);
                }
                return view;
            }
        };

        achievementList.setAdapter(arrayAdapter);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSecretClickCount++;

                if(mSecretClickCount == SECRET_CLICK_LIMIT) {
                    SweetAlertDialog dialog = new SweetAlertDialog(getContext(), SUCCESS_TYPE);
                    dialog.setTitleText("Good job!");
                    dialog.setConfirmText("See Achievements");
                    dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();

                            // Open achievements activity
                            Intent intent = new Intent(getContext(), AchievementActivity.class);
                            startActivity(intent);
                        }
                    });
                    dialog.setCancelButton("Close", new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();
                        }
                    });
                    dialog.show();
                    mSecretClickCount = 0;
                }
            }
        });
    }
}
