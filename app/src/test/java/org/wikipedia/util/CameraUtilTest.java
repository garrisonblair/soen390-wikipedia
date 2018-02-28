package org.wikipedia.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;

import org.junit.Test;
import org.wikipedia.settings.Prefs;
import org.wikipedia.settings.PrefsIoUtil;
import org.wikipedia.util.CameraUtil;

import java.io.File;
import java.io.IOException;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CameraUtilTest {

    public void testGetPublicStorageDirectory (){
        Context mockContext = mock(Context.class);
        CameraUtil cameraUtil = new CameraUtil();

        assertTrue(cameraUtil.getPublicStorageDirectory(mockContext).isDirectory());
    }

    @Test
    public void testGetPath (){
        CameraUtil cameraUtil = new CameraUtil();
        assertEquals(cameraUtil.getPath(), null);

        Context mockContext = mock(Context.class);
        File file = cameraUtil.getPublicStorageDirectory(mockContext);
        assertEquals(file.getPath(), "Wikipedia");
    }

}
