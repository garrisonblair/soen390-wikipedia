package org.wikipedia.imagesearch;

import java.io.Serializable;

/**
 * Created by steve on 01/03/18.
 */

public class WebEntity implements ImageRecognitionLabel, Serializable {


    private String description;
    private double score;

    public WebEntity(String description, double score) {
        this.description = description;
        this.score = score;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public double getScore() {
        return 0;
    }
}
