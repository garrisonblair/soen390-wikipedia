package org.wikipedia.relatedvideos;

import android.util.Log;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.YouTubeRequestInitializer;
import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Thumbnail;

import java.io.IOException;
import java.util.List;

/**
 * Created by KammyWong on 2018-03-23.
 */

public class YouTubeVideoService {
    private YouTube youtube;
    // System maximum to show is 50
    private final long maxVideos = 25;

    private final String apiKey = "AIzaSyC97eTu0rdwGuoJg0hv1r8No_55iaaeBp4";

    public YouTubeVideoService() {
        youtube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), new HttpRequestInitializer() {
            @Override
            public void initialize(HttpRequest httpRequest) throws IOException {
            }
        }).setYouTubeRequestInitializer(new YouTubeRequestInitializer(apiKey)).setApplicationName("Wikipedia-YouTube").build();
    }

    public List<SearchResult> searchVideos(String keyword) {
        Log.d("list", "keyword : " + keyword);
        try {
            // define the request for searching videos from YouTube using YouTube Data API
            YouTube.Search.List search = youtube.search().list("id,snippet");
            search.setQ(keyword);
            search.setType("video");
            // To increase efficiency, only retrieve the fields that the application uses.
            search.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)");
            search.setMaxResults(maxVideos);
            // Call the API
            SearchListResponse searchResponse = search.execute();
            return searchResponse.getItems();
        } catch (GoogleJsonResponseException e) {
            System.err.println("There was a service error: " + e.getDetails().getCode() + " : "
                    + e.getDetails().getMessage());
        } catch (IOException e) {
            System.err.println("There was an IO error: " + e.getCause() + " : " + e.getMessage());
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return null;
    }

    /*public List<String> listVideoTitles(List<SearchResult> searchResults) {
        int num = 0;
        System.out.println(searchResults.size());
        if (searchResults != null) {
            for (SearchResult sr : searchResults) {
                System.out.println(num);
                System.out.println(sr.getId());
                System.out.println(sr.getSnippet().getThumbnails().getDefault().getUrl());
                System.out.println(sr.getSnippet().getTitle());
                //SearchResult singleVideo = searchResult.next();
                //resourceId = sr.getId();
                // Confirm that the result represents a video. Otherwise, the title will not be showed
                //if (resourceId.getKind().equals("youtube#video")) {
                titles.add(sr.getSnippet().getTitle());
                    //titles.add(singleVideo.getSnippet().getDescription());
                //}
                num++;
            }
        }
        return titles;
    }

    public ResourceId getResourceId(SearchResult selectedResult) {
        return selectedResult.getId();
    }

    public String getThumbnail(SearchResult selectedResult) {
        Thumbnail thumbnail = selectedResult.getSnippet().getThumbnails().getDefault();
        return thumbnail.getUrl();
    }*/
}
