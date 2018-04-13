package org.wikipedia.userstatistics;

import android.os.Bundle;
import android.support.annotation.Nullable;

import org.wikipedia.R;
import org.wikipedia.WikipediaApp;
import org.wikipedia.activity.BaseActivity;
import org.wikipedia.util.ResourceUtil;


public class AchievementActivity extends BaseActivity {

    private WikipediaApp app;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (WikipediaApp) getApplicationContext();
        setStatusBarColor(ResourceUtil.getThemedAttributeId(this, R.attr.page_status_bar_color));

        setContentView(R.layout.activity_note);
        openAchievementFragment();
    }

    @Nullable
    private AchievementFragment achievementFragment() {
        return (AchievementFragment) getSupportFragmentManager()
                .findFragmentById(R.id.activity_note_container);
    }

    private void openAchievementFragment() {
        AchievementFragment fragment = achievementFragment();

        if (fragment == null) {
            fragment = AchievementFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.activity_note_container, fragment, "AchievementFragment")
                    .commit();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        app.getSessionFunnel().persistSession();
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
