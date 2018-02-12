package org.wikipedia.texttospeech;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.wikipedia.testutils.TestUtils;

import java.util.Locale;

import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Fred on 2018-02-05.
 * Tests TTSWrapper functionality
 */
@RunWith(MockitoJUnitRunner.class)
public class TTSWrapperTest {

    @Mock
    private TextToSpeech tts = mock(TextToSpeech.class);

    @Mock
    private Context context = mock(Context.class);

    @Mock
    private UtteranceProgressListener listener = mock(UtteranceProgressListener.class);

    private TTSWrapper ttsWrapper;

    private SharedPreferences sharedPrefs = Mockito.mock(SharedPreferences.class);

    @Before
    public void setUp() throws Throwable {
        TestUtils.setFinalStatic(Build.VERSION.class.getField("SDK_INT"), 22);
        ttsWrapper = TTSWrapper.getInstance(context, listener);
        ttsWrapper.setTTS(tts);
        when(context.toString()).thenReturn("context");
    }

    @Test
    public void testSpeakQueueMode() {
        ttsWrapper.setQueueMode(true);
        ttsWrapper.speak("Test");
        verify(tts).speak(eq("Test"), eq(TextToSpeech.QUEUE_ADD), eq(null), anyString());
    }

    @Test
    public void testSpeak() {
        ttsWrapper.speak("Test");
        verify(tts).speak(eq("Test"), eq(TextToSpeech.QUEUE_FLUSH), eq(null),  anyString());
    }

    @Test
    public void testGetVoices() {
        ttsWrapper.getVoices();
        verify(tts).getVoices();
    }

    @Test
    public void testSetLanguage() {
        ttsWrapper.setLanguage(Locale.CANADA_FRENCH);
        verify(tts).setLanguage(Locale.CANADA_FRENCH);
    }

    @Test
    public void testSingletonNoContextChange() {
        TTSWrapper firstWrapper = TTSWrapper.getInstance(context, listener);
        TextToSpeech tts1 = firstWrapper.getTTS();

        TTSWrapper secondWrapper = TTSWrapper.getInstance(context, listener);
        TextToSpeech tts2 = secondWrapper.getTTS();

        // both references should contain the same Object
        Assert.assertTrue(firstWrapper == secondWrapper);

        //the tts instance should not have been changed
        Assert.assertTrue(tts1 == tts2);
    }

    @Test
    public void testSingletonContextChange() {
        TTSWrapper firstWrapper = TTSWrapper.getInstance(context, listener);
        TextToSpeech tts1 = firstWrapper.getTTS();

        TTSWrapper secondWrapper = TTSWrapper.getInstance(mock(Context.class), listener);
        TextToSpeech tts2 = secondWrapper.getTTS();

        // both references should contain the same Object
        Assert.assertTrue(firstWrapper == secondWrapper);

        // because of the new context, the TextToSpeech instance should have changed
        Assert.assertFalse(tts1 == tts2);
    }

    @Before
    public void before() throws Exception {
        this.sharedPrefs = Mockito.mock(SharedPreferences.class);
        this.context = Mockito.mock(Context.class);
        Mockito.when(context.getSharedPreferences(anyString(), anyInt())).thenReturn(sharedPrefs);
    }

    @Test
    public void testGetValidPreferences() throws Exception {
        Mockito.when(sharedPrefs.getString(anyString(), anyString())).thenReturn(STRING);
        Assert.assertEquals(STRING, VALUE);
    }

}
