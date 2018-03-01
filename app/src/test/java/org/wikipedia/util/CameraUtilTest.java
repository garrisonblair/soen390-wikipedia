package org.wikipedia.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.speech.tts.TextToSpeech;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.wikipedia.settings.Prefs;
import org.wikipedia.settings.PrefsIoUtil;
import org.wikipedia.texttospeech.TTSWrapper;
import org.wikipedia.util.CameraUtil;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
