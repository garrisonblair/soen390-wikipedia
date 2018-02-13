package org.wikipedia.menu;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.wikipedia.R;
import org.wikipedia.page.PageActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by Andres on 2018-02-13.
 */

public class SelectedTextTest{

    @Rule
     public ActivityTestRule<PageActivity> activityRule = new ActivityTestRule(PageActivity.class);

    @Test
    public void ToSpeechUITest(){
        activityRule.getActivity();
        onView(withId(R.id.page_web_view)).perform(longClick());
        onView(withId(R.id.menu_text_to_speech)).perform(click());
        onView(withId(R.id.page_stop_button)).check(matches(isDisplayed()));
        onView(withId(R.id.page_stop_button)).perform(click());
        onView(withId(R.id.page_stop_button)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)));
    }
}
