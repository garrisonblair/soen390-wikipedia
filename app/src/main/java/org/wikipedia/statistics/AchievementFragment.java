package org.wikipedia.statistics;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.wikipedia.R;
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
                    dialog.show();
                    mSecretClickCount = 0;
                }
            }
        });

        view.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(getContext(), "Long Click", Toast.LENGTH_SHORT).show();

                int uniqueID = (int)System.currentTimeMillis();

                Intent intent = new Intent(getContext(), AchievementFragment.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 0, intent, 0);

                String title = "NEW ACHIEVEMENT";
                String description = "HELLO";
                String channelId = Integer.toString(uniqueID);

                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getContext(), channelId)
                        .setSmallIcon(R.drawable.icon_done)
                        .setContentTitle(title)
                        .setContentText(description)
                        .setPriority(NotificationCompat.PRIORITY_MAX)
                        .setAutoCancel(true)
                        .setContentIntent(pendingIntent);

//                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getContext());
                NotificationManager notificationManager = (NotificationManager) getContext().getSystemService(getContext().NOTIFICATION_SERVICE);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel channel = new NotificationChannel(channelId, "Channel human readable title", NotificationManager.IMPORTANCE_DEFAULT);
                    notificationManager.createNotificationChannel(channel);
                }

                notificationManager.notify(uniqueID, mBuilder.build());

                return true;
            }
        });
    }
}
