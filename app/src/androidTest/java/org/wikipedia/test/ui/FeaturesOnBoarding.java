package org.wikipedia.test.ui;


import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.wikipedia.R;
import org.wikipedia.main.MainActivity;
import org.wikipedia.onboarding.FeaturesOnboardingActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class FeaturesOnBoarding {

    @Rule
    public ActivityTestRule<FeaturesOnboardingActivity> activityRule = new ActivityTestRule(FeaturesOnboardingActivity.class);

    @Test
    public void onBoardingPageZero() {
        activityRule.getActivity();
        onView(ViewMatchers.withId(R.id.inflate_features_onboarding_page_zero)).check(matches(isDisplayed()));
    }

    @Test
    public void onBoardingPageOne() {
        activityRule.getActivity();
        onView(withId(R.id.inflate_features_onboarding_page_zero)).perform(swipeLeft());
        onView(withId(R.id.inflate_features_onboarding_page_one)).check(matches(isDisplayed()));
    }

    @Test
    public void onBoardingPageTwo() {
        activityRule.getActivity();
        // Wait for search response
        try{
            Thread.sleep(1000);
        }
        catch(InterruptedException e){

        }
        onView(withId(R.id.inflate_features_onboarding_page_zero)).perform(swipeLeft());
        // Wait for search response
        try{
            Thread.sleep(1000);
        }
        catch(InterruptedException e){

        }
        onView(withId(R.id.inflate_features_onboarding_page_one)).perform(swipeLeft());
        // Wait for search response
        try{
            Thread.sleep(1000);
        }
        catch(InterruptedException e){

        }
        onView(withId(R.id.inflate_features_onboarding_page_two)).check(matches(isDisplayed()));
    }


    @Test
    public void onBoardingPageThree() {
        activityRule.getActivity();
        // Wait for search response
        try{
            Thread.sleep(1000);
        }
        catch(InterruptedException e){

        }
        onView(withId(R.id.inflate_features_onboarding_page_zero)).perform(swipeLeft());
        // Wait for search response
        try{
            Thread.sleep(1000);
        }
        catch(InterruptedException e){

        }
        onView(withId(R.id.inflate_features_onboarding_page_one)).perform(swipeLeft());
        // Wait for search response
        try{
            Thread.sleep(1000);
        }
        catch(InterruptedException e){

        }
        onView(withId(R.id.inflate_features_onboarding_page_two)).perform(swipeLeft());
        // Wait for search response
        try{
            Thread.sleep(1000);
        }
        catch(InterruptedException e){

        }
        onView(withId(R.id.inflate_features_onboarding_page_three)).check(matches(isDisplayed()));
    }
}