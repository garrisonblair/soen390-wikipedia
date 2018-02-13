package org.wikipedia.onboarding;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import org.wikipedia.activity.SingleFragmentActivity;
import org.wikipedia.settings.Prefs;

public class FeaturesOnboardingActivity
        extends SingleFragmentActivity<FeaturesOnboardingFragment>
        implements FeaturesOnboardingFragment.Callback {

    @NonNull public static Intent newIntent(@NonNull Context context) {
        return new Intent(context, FeaturesOnboardingActivity.class);
    }

    @Override public void onComplete() {
        setResult(RESULT_OK);
        Prefs.setFeaturesOnboardingEnabled(false);
        finish();
    }

    @Override
    public void onBackPressed() {
        if (getFragment().onBackPressed()) {
            return;
        }
        finish();
    }

    @Override protected FeaturesOnboardingFragment createFragment() {
        return FeaturesOnboardingFragment.newInstance();
    }
}
