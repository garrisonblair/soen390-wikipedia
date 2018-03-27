package org.wikipedia.relatedvideos;

/**
 * Created by KammyWong on 2018-03-26.
 */

public class Video implements VideoInfo{
    private String title;
    private String videoID;
    private String description;
    private String url;

    Video() {
        this.title = "";
        this.videoID = "";
        this.description = "";
        this.url = "";
    }
    Video(String title, String videoId, String description, String url) {
        this.title = title;
        this.videoID = videoId;
        this.description = description;
        this.url = url;
    }

    public void setVideo(String title, String videoId, String description, String url) {
        this.title = title;
        this.videoID = videoId;
        this.description = description;
        this.url = url;
    }

    @Override
    public String getTitle() {
        return this.title;
    }
    @Override
    public String getID() {
        return this.videoID;
    }
    @Override
    public String getDescription() {
        return this.description;
    }
    @Override
    public String getURL() {
        return this.url;
    }
}
