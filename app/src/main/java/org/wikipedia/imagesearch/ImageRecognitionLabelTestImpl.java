package org.wikipedia.imagesearch;

/**
 * Created by Fred on 2018-02-26.
 */

public class ImageRecognitionLabelTestImpl implements ImageRecognitionLabel {

    private String description;
    private double score;

    public ImageRecognitionLabelTestImpl(String description, double score) {
        this.description = description;
        this.score = score;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public double getScore() {
        return score;
    }
}
