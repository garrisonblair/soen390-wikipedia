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
import java.util.Iterator;
import java.util.List;

/**
 * Created by KammyWong on 2018-03-23.
 */

public class YouTubeVideoService {
    private YouTube youtube;

    private final String apiKey = "AIzaSyC97eTu0rdwGuoJg0hv1r8No_55iaaeBp4";

    public YouTubeVideoService() {
        /*NetHttpTransport transport = new NetHttpTransport();
        JacksonFactory jsonFactory = new JacksonFactory();
        GoogleCredential credential = new GoogleCredential()
                .setAccessToken(apiKey);
        youtube = new YouTube.Builder(transport, jsonFactory,
                credential).setApplicationName("Wikipedia-YouTube").build();*/
        youtube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), new HttpRequestInitializer() {
            @Override
            public void initialize(HttpRequest httpRequest) throws IOException {
            }
        }).setYouTubeRequestInitializer(new YouTubeRequestInitializer(apiKey)).setApplicationName("Wikipedia-YouTube").build();
    }

    public List<SearchResult> searchVideos(String keyword) {
        Log.d("list", "keyword : " + keyword);
        final long maxVideos = 25;
        List<SearchResult> searchResultList = null;
        try {
            // Define the API request for retrieving search results.
            YouTube.Search.List search = youtube.search().list("id,snippet");
            //search.setKey(apiKey);
            search.setQ(keyword);

            // Restrict the search results to only include videos. See:
            // https://developers.google.com/youtube/v3/docs/search/list#type
            //search.setType("video");

            // To increase efficiency, only retrieve the fields that the application uses.
            search.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)");
            search.setMaxResults(maxVideos);

            // Call the API and print results
            SearchListResponse searchResponse = search.execute();
            searchResultList = searchResponse.getItems();
            Log.d("list", "Empty or not : " + search.isEmpty());
            //Log.d("list", "searched result : " + searchResultList.get(0).getSnippet().getTitle());
        } catch (GoogleJsonResponseException e) {
            System.err.println("There was a service error: " + e.getDetails().getCode() + " : "
                    + e.getDetails().getMessage());
        } catch (IOException e) {
            System.err.println("There was an IO error: " + e.getCause() + " : " + e.getMessage());
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return searchResultList;
    }

    public List<String> listVideoTitles(Iterator<SearchResult> searchResult) {
        List<String> titles = null;
        if (searchResult != null) {
            while (searchResult.hasNext()) {
                SearchResult singleVideo = searchResult.next();
                ResourceId resourceId = singleVideo.getId();
                // Confirm that the result represents a video. Otherwise, the title will not be showed
                if (resourceId.getKind().equals("youtube#video")) {
                    titles.add(singleVideo.getSnippet().getTitle());
                    //titles.add(singleVideo.getSnippet().getDescription());
                }
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
    }
}
