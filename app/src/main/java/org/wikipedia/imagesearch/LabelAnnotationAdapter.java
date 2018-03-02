package org.wikipedia.imagesearch;

import com.google.api.services.vision.v1.model.EntityAnnotation;

import java.io.Serializable;

/**
 * Created by steve on 01/03/18.
 */

public class LabelAnnotationAdapter implements ImageRecognitionLabel, Serializable {

    private String description;

    private double score;

    public LabelAnnotationAdapter(EntityAnnotation entityAnnotation) {
        this.description = entityAnnotation.getDescription();
        this.score = entityAnnotation.getScore();
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
