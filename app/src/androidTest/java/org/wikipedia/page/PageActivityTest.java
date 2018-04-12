package org.wikipedia.page;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.wikipedia.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;

/**
 * Created by Andres on 2018-04-12.
 */

@RunWith(AndroidJUnit4.class)
public class PageActivityTest {

    @Rule
    public ActivityTestRule<PageActivity> pageActivityRule = new ActivityTestRule(PageActivity.class);

    @Test
    public void notEmptyTreeTest() throws InterruptedException {

        pageActivityRule.getActivity();
        Thread.sleep(5000);
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        Thread.sleep(3000);
        onView(withText("View my journey")).perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.node_value)).check(matches(isDisplayed()));
    }
}
