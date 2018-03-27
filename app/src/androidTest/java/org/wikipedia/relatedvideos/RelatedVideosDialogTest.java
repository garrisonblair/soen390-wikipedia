package org.wikipedia.relatedvideos;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.wikipedia.R;

import java.util.ArrayList;
import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

import static org.mockito.Mockito.*;

/**
 * Created by Fred on 2018-03-26.
 */

@RunWith(AndroidJUnit4.class)
public class RelatedVideosDialogTest {

    private RelatedVideosDialog dialog;

    @Rule
    public ActivityTestRule<FragmentUtilActivity> activityRule = new ActivityTestRule<FragmentUtilActivity>(FragmentUtilActivity.class, false, true){};

    @Mock
    YouTubeVideoService mockVideoService = mock(YouTubeVideoService.class);

    @Before
    public void setup() {
    }

    @Test
    public void testVideoServiceCalledWithTitles() {
        String title = "Article Title";
        dialog = RelatedVideosDialog.newInstance(title, mockVideoService);
        activityRule.getActivity().setFragment(dialog);

        verify(mockVideoService).searchVideos(title, any());

        assertTrue(true);
    }

}
