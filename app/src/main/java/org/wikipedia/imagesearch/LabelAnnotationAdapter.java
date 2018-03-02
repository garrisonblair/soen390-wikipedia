package org.wikipedia.imagesearch;

import com.google.api.services.vision.v1.model.EntityAnnotation;

/**
 * Created by steve on 01/03/18.
 */

public class LabelAnnotationAdapter implements ImageRecognitionLabel {

    private EntityAnnotation entityAnnotation;

    public LabelAnnotationAdapter(EntityAnnotation entityAnnotation){

        this.entityAnnotation = entityAnnotation;
    }

    @Override
    public String getDescription() {

        return entityAnnotation.getDescription();
    }

    @Override
    public double getScore() {

        return entityAnnotation.getScore();
    }
}
