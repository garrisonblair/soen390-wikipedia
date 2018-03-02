package org.wikipedia.imagesearch;

import com.google.api.services.vision.v1.model.EntityAnnotation;

/**
 * Created by steve on 01/03/18.
 */

public class LabelAnnotationAdapter implements ImageRecognitionLabel {

    private EntityAnnotation annotation;

    public LabelAnnotationAdapter(EntityAnnotation annotation) {
        this.annotation = annotation;
    }

    @Override
    public String getDescription() {
        return annotation.getDescription();
    }

    @Override
    public double getScore() {
        return annotation.getScore();
    }
}
