package org.wikipedia.feed.searchbar;

import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.wikipedia.R;
import org.wikipedia.main.MainActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


public class SearchCardViewTest {

    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule(MainActivity.class);

    @Test
    public void onCameraImageSearchClick() {
        activityRule.getActivity();
        onView(withId(R.id.image_search_open_camera_button)).perform(click());
    }

    @Test
    public void onGallerySearchClick() {
        activityRule.getActivity();
        onView(withId(R.id.gallery_search_button)).perform(click());
    }
}
