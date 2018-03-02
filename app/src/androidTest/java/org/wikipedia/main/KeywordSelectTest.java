package org.wikipedia.main;


import android.support.test.espresso.DataInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.wikipedia.R;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class KeywordSelectTest {


    @Rule
    public ActivityTestRule<TestKeywordSelectActivity> mActivityTestRule = new ActivityTestRule<TestKeywordSelectActivity>(TestKeywordSelectActivity.class){
    };


    //This test starts in the TestKeywordSelectActivity, which starts the KeywordSelectActivity.  It checks that the selected value is properly returned.
    @Test
    public void keywordSelectTest() {

        onView(withId(R.id.fab)).perform(click());

        try{
            Thread.sleep(2000);
        } catch(Exception e) {

        }

        onView(withText("Cat")).check(matches(isDisplayed()));
        onView(withText("Dog")).check(matches(isDisplayed()));

        DataInteraction constraintLayout = onData(anything())
                .inAdapterView(allOf(withId(R.id.keyword_list_view),
                        childAtPosition(
                                withClassName(is("android.support.constraint.ConstraintLayout")),
                                0)))
                .atPosition(1);

        constraintLayout.perform(click());

        try{
            Thread.sleep(2000);
        } catch(Exception e) {

        }

        onView(allOf(withText("Dog"), withId(R.id.test_result_view))).check(matches(isDisplayed()));

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
