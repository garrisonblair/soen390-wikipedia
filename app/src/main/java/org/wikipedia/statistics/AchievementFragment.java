package org.wikipedia.statistics;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        getView().findViewById(R.id.fragment_achievement_title).setOnClickListener(new View.OnClickListener() {
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
    }
}
