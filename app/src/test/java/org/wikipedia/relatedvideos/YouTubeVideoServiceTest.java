package org.wikipedia.relatedvideos;

import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.SearchResultSnippet;
import com.google.api.services.youtube.model.Thumbnail;
import com.google.api.services.youtube.model.ThumbnailDetails;

import org.junit.Assert;
import org.junit.Test;
import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KammyWong on 2018-03-25.
 */

public class YouTubeVideoServiceTest {

    @Test
    public void setCallbackTest() throws Exception {
        YouTubeVideoService service = new YouTubeVideoService();
        YouTubeVideoService.Callback callbackMock = mock(YouTubeVideoService.Callback.class);
        service.setCallback(callbackMock);
        Assert.assertEquals(callbackMock, service.getCallback());
    }

    @Test
    public void getCallbackTest() throws Exception {

        YouTubeVideoService serviceMock = mock(YouTubeVideoService.class);
        YouTubeVideoService.Callback callbackMock = mock(YouTubeVideoService.Callback.class);
        serviceMock.setCallback(callbackMock);
        when(serviceMock.getCallback()).thenReturn(callbackMock);
        Assert.assertEquals(callbackMock, serviceMock.getCallback());
    }

    @Test
    public void getAllVideoInfoTest() throws Exception {
        SearchResult result = new SearchResult();
        result.setKind("youtube#youTubeVideoAdapter");

        ResourceId resourceId = new ResourceId();
        resourceId.setVideoId("123");
        result.setId(resourceId);

        SearchResultSnippet snippet = new SearchResultSnippet();
        snippet.setTitle("Loving cat");
        snippet.setDescription("Show my loving cat");

        ThumbnailDetails thumbnailDetails = new ThumbnailDetails();
        Thumbnail thumbnail = new Thumbnail();
        thumbnail.setUrl("http://youtube.cat");
        thumbnailDetails.setDefault(thumbnail);
        snippet.setThumbnails(thumbnailDetails);

        result.setSnippet(snippet);

        List<SearchResult> results = new ArrayList<>();
        results.add(result);

        YouTubeVideoService service = new YouTubeVideoService();
        List<VideoInfo> videos = new ArrayList<>();
        videos = service.getAllVideoInfo(results);
        assertEquals("Loving cat", videos.get(0).getTitle());
        assertEquals("123", videos.get(0).getID());
    }
}