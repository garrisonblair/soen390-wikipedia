package org.wikipedia.relatedvideos;

import com.google.api.services.youtube.model.SearchResult;

/**
 * Created by KammyWong on 2018-03-26.
 */

public class YouTubeVideoAdapter implements VideoInfo{
    private SearchResult result;

    public YouTubeVideoAdapter(SearchResult result) {
        this.result = result;
    }

    public void setVideo(SearchResult result) {
        this.result = result;
    }

    @Override
    public String getTitle() {
        return this.result.getSnippet().getTitle();
    }
    @Override
    public String getID() {
        return result.getId().getVideoId();
    }
    @Override
    public String getDescription() {
        return result.getSnippet().getDescription();
    }
    @Override
    public String getURL() {
        return result.getSnippet().getThumbnails().getHigh().getUrl();
    }
}
