package org.wikipedia.imagesearch;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.api.services.vision.v1.Vision;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesResponse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.io.File;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


/**
 * Created by steve on 01/03/18.
 */
@RunWith(RobolectricTestRunner.class)
public class ImageRecognitionServiceTest {
    @Test
    public void setCallbackTest() throws Exception {

        ImageRecognitionService imageRecognitionService = new ImageRecognitionService();
        ImageRecognitionService.Callback callback = mock(ImageRecognitionService.Callback.class);
        imageRecognitionService.setCallback(callback);
        assertEquals(callback, imageRecognitionService.getCallback());
    }

    @Test
    public void getCallbackTest() throws Exception {

        ImageRecognitionService imageRecognitionService = mock(ImageRecognitionService.class);
        ImageRecognitionService.Callback callback = mock(ImageRecognitionService.Callback.class);
        imageRecognitionService.setCallback(callback);
        when(imageRecognitionService.getCallback()).thenReturn(callback);
        assertEquals(callback, imageRecognitionService.getCallback());
    }

    @Test
    public void executeImageRecognitionTest() throws Exception {


        File file = new File("Cat03.jpg");

        String path = file.getAbsolutePath();

        Bitmap bitmap = BitmapFactory.decodeFile(path);

        ImageRecognitionService.Callback callback = mock(ImageRecognitionService.Callback.class);
        Vision vision = mock(Vision.class);
        Vision.Images visionImages = mock(Vision.Images.class);
        when(vision.images()).thenReturn(visionImages);
        Vision.Images.Annotate annotateRequest = mock(Vision.Images.Annotate.class);
        when(visionImages.annotate(any())).thenReturn(annotateRequest);
        BatchAnnotateImagesResponse batchAnnotateImagesResponse = new BatchAnnotateImagesResponse();
        when(annotateRequest.execute()).thenReturn(batchAnnotateImagesResponse);

        ImageRecognitionService imageRecognitionService = new ImageRecognitionService(vision);
        imageRecognitionService.executeImageRecognition(bitmap, callback);

        verify(vision).images();
        verify(visionImages).annotate(any());
        verify(annotateRequest).execute();

    }
}