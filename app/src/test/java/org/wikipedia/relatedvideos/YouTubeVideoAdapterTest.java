package org.wikipedia.relatedvideos;

import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.SearchResultSnippet;
import com.google.api.services.youtube.model.Thumbnail;
import com.google.api.services.youtube.model.ThumbnailDetails;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by KammyWong on 2018-03-26.
 */

public class YouTubeVideoAdapterTest {
    YouTubeVideoAdapter youTubeVideoAdapter;
    @Before
    public void beforeTest(){
        SearchResult result = new SearchResult();
        result.setKind("youtube#video");

        ResourceId resourceId = new ResourceId();
        resourceId.setVideoId("123");
        result.setId(resourceId);

        SearchResultSnippet snippet = new SearchResultSnippet();
        snippet.setTitle("Loving cat");
        snippet.setDescription("Show my loving cat");

        ThumbnailDetails thumbnailDetails = new ThumbnailDetails();
        Thumbnail thumbnail = new Thumbnail();
        thumbnail.setUrl("http://youtube.com/cat");
        thumbnailDetails.setDefault(thumbnail);
        snippet.setThumbnails(thumbnailDetails);

        result.setSnippet(snippet);

        youTubeVideoAdapter = new YouTubeVideoAdapter(result);
    }

    @Test
    public void getTitleTest(){
        assertEquals("Loving cat", youTubeVideoAdapter.getTitle());
    }

    @Test
    public void getIDTest(){
        assertEquals("123", youTubeVideoAdapter.getID());
    }

    @Test
    public void getDescriptionTest(){
        assertEquals("Show my loving cat", youTubeVideoAdapter.getDescription());
    }

    @Test
    public void getURLTest(){
        assertEquals("http://youtube.com/cat", youTubeVideoAdapter.getURL());
    }
}
