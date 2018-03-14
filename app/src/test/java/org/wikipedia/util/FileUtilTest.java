package org.wikipedia.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import static org.mockito.Mockito.*;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runners.JUnit4;
import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * Created by Fred on 2018-03-05.
 */

public class FileUtilTest {

    @Test
    public void test() {
        assertTrue(true);
    }

    @Test
    public void testReadJavascriptFile() {
        try {
            //create test file and inputStream for that file
            InputStream inputStream;
            String path = "testfile.js";

            PrintWriter printWriter = new PrintWriter(path, "UTF-8");

            printWriter.println("Test \nString");

            printWriter.close();


            inputStream = new FileInputStream(path);


            //mock context to provide test Stream
            Context context = mock(Context.class);
            AssetManager assetManager = mock(AssetManager.class);
            when(context.getAssets()).thenReturn(assetManager);
            when(assetManager.open(path)).thenReturn(inputStream);


            //method being tested
            String result = FileUtil.readJavascriptFile(context, path);

            verify(context).getAssets();
            verify(assetManager).open(path);
            assertEquals("Test String", result);
            return;
        } catch (Exception e) {
            String message = e.getMessage();
            Log.d("ERR", "Error with File IO: " + message);
        }

        fail();
    }
}
