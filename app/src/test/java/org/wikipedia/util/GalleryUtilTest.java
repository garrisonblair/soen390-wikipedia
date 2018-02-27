package org.wikipedia.util;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.wikipedia.test.TestRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Andres on 2018-02-26.
 */

@RunWith(TestRunner.class)
public class GalleryUtilTest {

    @Test
    public void newGalleryPickIntentTest(){
        Intent intent = GalleryUtil.newGalleryPickIntent();
        assertEquals("image/*",intent.getType());
    }

    @Test
    public void getSelectedPictureTest(){
        Intent intentMock = mock(Intent.class);
        Uri uri = mock(Uri.class);

        //constant should be -1
        assertEquals(Activity.RESULT_OK, -1);
        final int OK = Activity.RESULT_OK;

        //constant should be 0
        assertEquals(Activity.RESULT_CANCELED, 0);
        final int CANCEL = Activity.RESULT_CANCELED;

        when(intentMock.getData()).thenReturn(uri);
        Activity activityMock = mock(Activity.class);

        Bitmap bitmap = GalleryUtil.getSelectedPicture(OK, null,activityMock);
        assertEquals(bitmap,null);

        bitmap = GalleryUtil.getSelectedPicture(CANCEL, intentMock,activityMock);
        assertEquals(bitmap,null);

        bitmap = GalleryUtil.getSelectedPicture(OK, intentMock, activityMock);
        assertTrue(bitmap != null);
    }
}