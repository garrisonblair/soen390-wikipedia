package org.wikipedia.util;

import android.content.Context;
import android.content.Intent;

import org.junit.Test;
import org.wikipedia.util.CameraUtil;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CameraUtilTest {

    @Test
    public void testTakePhoto() throws IOException{
        CameraUtil cameraUtil = new CameraUtil();
        Context mockContext = mock(Context.class);
        File mockFile = mock(File.class);

        assertEquals(null, cameraUtil.getPath());

        when(cameraUtil.createPhotoFile(mockContext)).thenReturn(mockFile);
        Intent intent = cameraUtil.takePhoto(mockContext);

        //Test if there is an activity of creating file, file name and path
        assertFalse(cameraUtil.getPath().isEmpty());

        File file = new File(cameraUtil.getPath());
        file.delete();
    }
}
