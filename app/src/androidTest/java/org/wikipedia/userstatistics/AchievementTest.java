package org.wikipedia.userstatistics;

import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.wikipedia.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class AchievementTest {

    @Rule
    public ActivityTestRule activityRule = new ActivityTestRule<>(
            AchievementActivity.class,
            true,    // initialTouchMode
            true);  // launchActivity.

    @Test
    public void openAchievement() {
        waitDisplay();
        onView(withId(R.id.fragment_achievement_title)).check(matches(isDisplayed()));
    }

    public void waitDisplay() {
        try{
            Thread.sleep(1000);
        }
        catch(InterruptedException e){

        }
    }
}
