package org.wikipedia.relatedvideos;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

/**
 * Created by Fred on 2018-03-26.
 */

public class FragmentUtilActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout view = new LinearLayout(this);
        view.setId(1);

        setContentView(view);
    }

    public void setFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().add(fragment, "TEST")
        .commit();
    }
}
