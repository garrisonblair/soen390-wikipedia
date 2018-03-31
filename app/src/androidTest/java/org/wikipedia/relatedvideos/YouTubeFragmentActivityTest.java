package org.wikipedia.relatedvideos;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.Button;
import android.widget.TextView;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.wikipedia.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by steve on 30/03/18.
 */

@RunWith(AndroidJUnit4.class)
public class YouTubeFragmentActivityTest {

    @Rule
    public ActivityTestRule<YouTubeFragmentActivityTestActivity> activityRule = new ActivityTestRule<YouTubeFragmentActivityTestActivity>(YouTubeFragmentActivityTestActivity.class, false, true){};

    @Test
    public void testYouTubeFragmentActivity() {

        //setContentView(R.layout.activity_you_tube_fragment_test);
        Button button = activityRule.getActivity().findViewById(R.id.testButton);
        button.callOnClick();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //YouTubePlayerFragment videoFragmentView = (YouTubePlayerFragment)activityRule.getActivity().getFragmentManager().findFragmentById(R.id.youtube_fragment); //
        TextView videoTitleView = activityRule.getActivity().findViewById(R.id.youtube_video_title);
        TextView videoDescriptionView = activityRule.getActivity().findViewById(R.id.youtube_video_description);

        onView(withText("Test Page Title")).check(matches(isDisplayed()));
        onView(withText("Test Video Description")).check(matches(isDisplayed()));



    }
}
