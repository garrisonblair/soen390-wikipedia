package org.wikipedia.imagesearch;

/**
 * Created by steve on 01/03/18.
 */

public class WebEntity implements ImageRecognitionLabel {


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
