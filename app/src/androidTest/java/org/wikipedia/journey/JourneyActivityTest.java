package org.wikipedia.journey;

import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.wikipedia.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by Andres on 2018-04-12.
 */

public class JourneyActivityTest {

    @Rule
    public ActivityTestRule<JourneyActivity> activityRule = new ActivityTestRule(JourneyActivity.class);

    @Test
    public void emptyTreeTest() throws InterruptedException {
        activityRule.getActivity();
        Thread.sleep(2000);
        onView(withId(R.id.nothing_to_display)).check(matches(isDisplayed()));
        Thread.sleep(2000);
    }
}
