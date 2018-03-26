package org.wikipedia.relatedvideos;

import com.google.api.services.youtube.model.SearchResult;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

import java.util.List;

/**
 * Created by KammyWong on 2018-03-25.
 */

public class YouTubeVideoServiceTest {
    private YouTubeVideoService service;
    List<SearchResult> listResults;

    @Before
    public void initialYouTubeVideoService(){
        service = new YouTubeVideoService();
        listResults = null;
    }

    @Test
    public void testSearchVideos(){
        listResults = service.searchVideos("cat");
        assertFalse(listResults.isEmpty());
        assertEquals(25, listResults.size());
        int num = 0;
        for (SearchResult sr : listResults) {
            System.out.println(num);
            System.out.println(sr.getId());
            System.out.println(sr.getSnippet().getThumbnails().getDefault().getUrl());
            System.out.println(sr.getSnippet().getTitle());
            num++;
        }
    }
}