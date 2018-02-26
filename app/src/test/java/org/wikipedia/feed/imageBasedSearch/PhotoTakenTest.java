package org.wikipedia.feed.imageBasedSearch;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.FileProvider;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.wikipedia.R;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.core.IsInstanceOf.any;
import static org.hamcrest.text.IsEmptyString.isEmptyString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PhotoTakenTest {

    @Test
    public void testTakePhoto() throws IOException{
        PhotoTaken photoTaken = new PhotoTaken();
        Context mockContext = mock(Context.class);
        File mockFile = mock(File.class);

        assertEquals(null,photoTaken.getPath());

        when(photoTaken.createPhotoFile(mockContext)).thenReturn(mockFile);
        Intent intent = photoTaken.takePhoto(mockContext);

        //Test if there is an activity of creating file, file name and path
        assertFalse(photoTaken.getPath().isEmpty());

        File file = new File(photoTaken.getPath());
        file.delete();
    }
}
