package org.wikipedia.imagesearch;

import junit.framework.Assert;

import org.junit.Test;

/**
 * Created by Fred on 2018-02-26.
 */

public class ImageRecognitionLabelTest {

    @Test
    public void testImageRecognitionLabel() {
        ImageRecognitionLabel label = new ImageRecognitionLabelTestImpl("Cat", 0.99);

        Assert.assertEquals(label.getDescription(), "Cat");
        Assert.assertEquals(label.getScore(), 0.99);
    }
}
