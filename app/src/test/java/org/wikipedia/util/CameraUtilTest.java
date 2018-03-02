package org.wikipedia.util;

import android.content.Context;

import org.junit.Test;

import java.io.File;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class CameraUtilTest {

    @Test
    public void getPathTest() {
        CameraUtil cameraUtil = new CameraUtil();
        assertEquals(null, cameraUtil.getPath());
        cameraUtil.setPath("wikipedia");
        assertEquals("wikipedia",cameraUtil.getPath());
    }

    @Test
    public void testGetPublicStorageDirectory (){
        Context mockContext = mock(Context.class);
        CameraUtil mockCameraUtil = mock(CameraUtil.class);
        File file = mockCameraUtil.getPublicStorageDirectory(mockContext);
        verify(mockCameraUtil).getPublicStorageDirectory(mockContext);
    }

    @Test
    public void testAddPhoto(){
        CameraUtil mockCameraUtil = mock(CameraUtil.class);
        Context mockContext = mock(Context.class);
        String path = "wikipedia";
        mockCameraUtil.addPhotoToGallery(mockContext, path);
        verify(mockCameraUtil).addPhotoToGallery(mockContext, path);
    }

}
