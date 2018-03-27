package org.wikipedia.relatedvideos;

/**
 * Created by Fred on 2018-03-25.
 */

public class VideoInfoTestImpl implements VideoInfo {

    private String id;
    private String title;
    private String description;
    private String thumbnailURL;

    public VideoInfoTestImpl(String id, String title, String description, String thumbnailURL) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.thumbnailURL = thumbnailURL;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getID() {
        return id;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getURL() {
        return thumbnailURL;
    }


}
