package org.wikipedia.imagesearch;

import com.google.api.services.vision.v1.model.EntityAnnotation;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

/**
 * Created by steve on 01/03/18.
 */
public class LabelAnnotationAdapterTest {
    @Test
    public void getDescriptionTest() throws Exception {

        String testString = "Test String";
        EntityAnnotation entityAnnotation = new EntityAnnotation();
        entityAnnotation.setDescription(testString);
        assertEquals(testString, entityAnnotation.getDescription());
    }

    @Test
    public void getScoreTest() throws Exception {

        Float testScore = (float)9.9;
        EntityAnnotation entityAnnotation = new EntityAnnotation();
        entityAnnotation.setScore(testScore);
        assertEquals(testScore, entityAnnotation.getScore());

    }

}