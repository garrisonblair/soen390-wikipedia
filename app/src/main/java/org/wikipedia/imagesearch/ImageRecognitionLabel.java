package org.wikipedia.imagesearch;

import java.io.Serializable;

/**
 * Created by Fred on 2018-02-26.
 */

public interface ImageRecognitionLabel extends Serializable{

    String getDescription();
    double getScore();
}
