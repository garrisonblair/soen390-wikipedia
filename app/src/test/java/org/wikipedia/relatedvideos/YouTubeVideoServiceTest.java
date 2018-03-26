package org.wikipedia.relatedvideos;

import com.google.api.services.youtube.model.SearchResult;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.List;

/**
 * Created by KammyWong on 2018-03-25.
 */

public class YouTubeVideoServiceTest {
    private YouTubeVideoService service;

    @Before
    public void initialYouTubeVideoService(){
        service = new YouTubeVideoService();
    }

    @Test
    public void testStartYouTubeAPI(){

    }

    @Ignore
    public void testRelateVideos(){
        //service.startExecution("cat");
        //List<SearchResult> results = service.getSearchResult();
        //assertFalse(results.isEmpty());
        //assertEquals(25, results.size());
    }

    @Ignore
    public void testGetAllVideoInfo() {
        //service.startExecution("cat");
        //List<Video> videos = service.getAllVideoInfo();
        //assertFalse(videos.isEmpty());
        //assertEquals(25, videos.size());
    }
}