package org.wikipedia.imagesearch;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.telecom.Call;

import com.google.api.services.vision.v1.Vision;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;


/**
 * Created by steve on 01/03/18.
 */
public class ImageRecognitionServiceTest {
    @Test
    public void setCallback() throws Exception {
    }

    @Test
    public void getCallback() throws Exception {



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

        ImageRecognitionService imageRecognitionService = new ImageRecognitionService(vision);
        imageRecognitionService.executeImageRecognition(bitmap, callback);



//        doReturn(bitmap).when(imageRecognitionService.scaleDown(bitmap,ImageRecognitionService.MAX_DIMENSION));
//        when(imageRecognitionService.scaleDown(bitmap,ImageRecognitionService.MAX_DIMENSION)).doReturn(bitmap);


        //verify(vision).images();
        verify(visionImages).annotate(any());

    }

}