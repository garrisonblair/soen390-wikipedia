package org.wikipedia.journey;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.wikipedia.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by Andres on 2018-04-12.
 */

@RunWith(AndroidJUnit4.class)
public class JourneyActivityTest {

    @Rule
    public ActivityTestRule<JourneyActivity> journeyActivityRule = new ActivityTestRule(JourneyActivity.class);

    @Test
    public void emptyTreeTest() throws InterruptedException {
        journeyActivityRule.getActivity();
        Thread.sleep(4000);
        onView(withId(R.id.nothing_to_display)).check(matches(isDisplayed()));
        Thread.sleep(4000);
    }
}
