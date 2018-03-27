package org.wikipedia.relatedvideos;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.YouTubeRequestInitializer;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by KammyWong on 2018-03-23.
 */

public class YouTubeVideoService {
    public interface Callback{
        void onYouTubeAPIResult(List<VideoInfo> list);
    }
    private YouTube youtube;
    private final long maxVideos = 25;
    private Callback callback;
    private List<SearchResult> searchResults = new ArrayList<>();
    private List<VideoInfo> videos = new ArrayList<>();

    private final String apiKey = "AIzaSyC97eTu0rdwGuoJg0hv1r8No_55iaaeBp4";

    public YouTubeVideoService() {
        youtube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), new HttpRequestInitializer() {
            @Override
            public void initialize(HttpRequest httpRequest) throws IOException {
            }
        }).setYouTubeRequestInitializer(new YouTubeRequestInitializer(apiKey)).setApplicationName("Wikipedia-YouTube").build();
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public Callback getCallback() {
        return callback;
    }

    public void searchVideos(String keyword, Callback callback) {
        if (!keyword.equals("")) {
            setCallback(callback);
            callYouTubeAPI(keyword);
        }
    }

    @SuppressLint("StaticFieldLeak")
    private void callYouTubeAPI(String pageTitle) {
        new AsyncTask<Object, Void, List<VideoInfo>>() {
            @Override
            protected List<VideoInfo> doInBackground(Object... params) {
                try {
                    // define the request for searching videos from YouTube using YouTube Data API
                    YouTube.Search.List search = youtube.search().list("id,snippet");
                    search.setQ(pageTitle);
                    search.setType("video");
                    // To increase efficiency, only retrieve the fields that the application uses.
                    search.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url,snippet/description)");
                    search.setMaxResults(maxVideos);
                    // Execute the API
                    SearchListResponse searchResponse = search.execute();
                    //List<SearchResult> searchResults = searchResponse.getItems();
                    searchResults = searchResponse.getItems();
                    for (SearchResult result : searchResults) {
                        System.out.println(result.getSnippet().getTitle());
                    }
                    videos = getAllVideoInfo(searchResults);
                    return videos;
                } catch (GoogleJsonResponseException e) {
                    System.err.println("Service error: " + e.getDetails().getCode() + " : "
                            + e.getDetails().getMessage());
                } catch (IOException e) {
                    System.err.println("IO error: " + e.getCause() + " : " + e.getMessage());
                } catch (Throwable t) {
                    t.printStackTrace();
                }
                return null;
            }

            protected void onPostExecute(List<YouTubeVideoAdapter> results) {
                callback.onYouTubeAPIResult(videos);
            }
        }.execute();
    }

    public List<VideoInfo> getAllVideoInfo(List<SearchResult> searchResults) {
        //List<VideoInfo> video = new ArrayList<>();
        if (searchResults != null) {
            for (SearchResult result : searchResults) {
                if (result.getKind().equals("youtube#video")) {
                    YouTubeVideoAdapter youTubeVideoAdapter = new YouTubeVideoAdapter(result);
                    videos.add(youTubeVideoAdapter);
                }
            }
        }
        return videos;
    }
}
