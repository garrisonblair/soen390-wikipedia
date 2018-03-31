package org.wikipedia.relatedvideos;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
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
    private YouTubeVideoService mockVideoService = mock(YouTubeVideoService.class);

    @Before
    public void setup() {
    }

    @Test
    public void testVideoServiceCalledWithTitles() {
        String title = "Article Title";
        dialog = RelatedVideosDialog.newInstance(title, mockVideoService);
        activityRule.getActivity().setFragment(dialog);

        //Let activity load
        try {
            Thread.sleep(2000);
        } catch(Exception e) {
            Log.d("ERROR", e.getMessage());
        }

        verify(mockVideoService).searchVideos(eq(title), any());

    }

    @Test
    public void testVideoTitlesDisplayed() {
        String title = "Article Title";

        //manually calling the Callback with sample results
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {

                ArrayList<VideoInfo> videos = new ArrayList<VideoInfo>();
                videos.add(new VideoInfoTestImpl("DaOJv-fMlmA", "Video1", "", "https://img.youtube.com/vi/DaOJv-fMlmA/0.jpg"));
                videos.add(new VideoInfoTestImpl("jI8Im6RoPWo", "Video2", "", "https://img.youtube.com/vi/jI8Im6RoPWo/0.jpg"));

                ((YouTubeVideoService.Callback) invocation.getArguments()[1]).onYouTubeAPIResult(videos);
                return null;
            }
        }).when(mockVideoService).searchVideos(eq(title), any(YouTubeVideoService.Callback.class));

        dialog = RelatedVideosDialog.newInstance(title, mockVideoService);
        activityRule.getActivity().setFragment(dialog);

        //Check that all titles are displayed
        onView(withText("Video1")).check(matches(isDisplayed()));
        onView(withText("Video2")).check(matches(isDisplayed()));
    }

}
