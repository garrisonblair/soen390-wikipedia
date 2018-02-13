package org.wikipedia.main;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.wikipedia.settings.SettingsActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class TTSSettingsUITests {

    @Rule
    public ActivityTestRule<SettingsActivity> mActivityTestRule = new ActivityTestRule<>(SettingsActivity.class);

    @Test
    public void tTSSettingsUITests() {
        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        ViewInteraction textView = onView(
                allOf(withText("Text to Speech Settings"),
                        isDisplayed()));
        textView.check(matches(withText("Text to Speech Settings")));

        ViewInteraction textView2 = onView(
                allOf(withText("Text to Speech Settings"),
                        isDisplayed()));
        textView2.check(matches(isDisplayed()));

        ViewInteraction textView3 = onView(
                allOf(withId(android.R.id.title), withText("Voice"),
                        isDisplayed()));
        textView3.check(matches(withText("Voice")));

        ViewInteraction textView4 = onView(
                allOf(withId(android.R.id.title), withText("Voice"),
                        isDisplayed()));
        textView4.check(matches(isDisplayed()));

        ViewInteraction textView5 = onView(
                allOf(withId(android.R.id.summary), withText("Select available text-to-speech voice"),
                        isDisplayed()));
        textView5.check(matches(withText("Select available text-to-speech voice")));

        ViewInteraction textView6 = onView(
                allOf(withId(android.R.id.summary), withText("Select available text-to-speech voice"),
                        isDisplayed()));
        textView6.check(matches(isDisplayed()));

        ViewInteraction textView7 = onView(
                allOf(withId(android.R.id.title), withText("Pitch"),
                        isDisplayed()));
        textView7.check(matches(withText("Pitch")));

        ViewInteraction textView8 = onView(
                allOf(withId(android.R.id.title), withText("Pitch"),
                        isDisplayed()));
        textView8.check(matches(isDisplayed()));

        ViewInteraction textView9 = onView(
                allOf(withId(android.R.id.summary), withText("Sets the pitch selected voice"),
                        isDisplayed()));
        textView9.check(matches(withText("Sets the pitch selected voice")));

        ViewInteraction textView10 = onView(
                allOf(withId(android.R.id.summary), withText("Sets the pitch selected voice"),
                        isDisplayed()));
        textView10.check(matches(isDisplayed()));

        ViewInteraction seekBar = onView(
                allOf(withId(org.wikipedia.R.id.seekbar),
                        isDisplayed()));
        seekBar.check(matches(isDisplayed()));

        // Espresso unable to access Speed elements
        /*
        ViewInteraction textView11 = onView(
                allOf(withId(android.R.id.title), withText("Speed"),
                        isDisplayed()));
        textView11.check(matches(withText("Speed")));

        ViewInteraction textView12 = onView(
                allOf(withId(android.R.id.title), withText("Speed"),
                        isDisplayed()));
        textView12.check(matches(isDisplayed()));

        ViewInteraction textView13 = onView(
                allOf(withId(android.R.id.summary), withText("Sets the speed of the selected voice"),
                        isDisplayed()));
        textView13.check(matches(withText("Sets the speed of the selected voice")));

        ViewInteraction textView14 = onView(
                allOf(withId(android.R.id.summary), withText("Sets the speed of the selected voice"),
                        isDisplayed()));
        textView14.check(matches(isDisplayed()));

        */
        ViewInteraction seekBar2 = onView(
                allOf(withId(org.wikipedia.R.id.seekbar),
                        isDisplayed()));
        seekBar2.check(matches(isDisplayed()));

        // Espresso unable to access Queue elements
        /*
        ViewInteraction textView15 = onView(
                allOf(withId(android.R.id.title), withText("Queue"),
                        isDisplayed()));
        textView15.check(matches(withText("Queue")));

        ViewInteraction textView16 = onView(
                allOf(withId(android.R.id.title), withText("Queue"),
                        isDisplayed()));
        textView16.check(matches(isDisplayed()));

        ViewInteraction textView17 = onView(
                allOf(withId(android.R.id.summary), withText("Enable queue mode"),
                        isDisplayed()));
        textView17.check(matches(withText("Enable queue mode")));

        ViewInteraction textView18 = onView(
                allOf(withId(android.R.id.summary), withText("Enable queue mode"),
                        isDisplayed()));
        textView18.check(matches(isDisplayed()));

        ViewInteraction switch_ = onView(
                allOf(withId(org.wikipedia.R.id.switchWidget),
                        isDisplayed()));
        switch_.check(matches(isDisplayed()));
        */
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
