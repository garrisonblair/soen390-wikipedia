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

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyFloat;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;

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

    @Mock
    private SharedPreferences sharedPrefs = mock(SharedPreferences.class);

    private TTSWrapper ttsWrapper;

    @Before
    public void setUp() throws Throwable {
        TTSWrapper.reset();
        TestUtils.setFinalStatic(Build.VERSION.class.getField("SDK_INT"), 22);
        ttsWrapper = TTSWrapper.getTestInstance(context, listener, sharedPrefs);
        ttsWrapper.setTTS(tts);
    }

    @Test
    public void testGetValidPreferences() throws Exception {
        final int base = 25;
        Mockito.when(sharedPrefs.getBoolean("ttsQueue", false)).thenReturn(false);

        TextToSpeech tts1 = mock(TextToSpeech.class);
        TTSWrapper wrapper = TTSWrapper.getTestInstance(context, listener, sharedPrefs);
        wrapper.setTTS(tts1);
        reset(sharedPrefs);
        wrapper.loadPreferences();

        verify(sharedPrefs).getString("ttsVoice", "");
        verify(sharedPrefs).getInt("ttsPitch", base);
        verify(sharedPrefs).getInt("ttsSpeed", base);
        verify(sharedPrefs).getBoolean("ttsQueue", false);

        verify(tts1).setVoice(any());
        verify(tts1).setPitch(anyFloat());
        verify(tts1).setSpeechRate(anyFloat());
        Assert.assertFalse(wrapper.getQueueMode());
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
        TTSWrapper firstWrapper = TTSWrapper.getTestInstance(context, listener, sharedPrefs);
        TextToSpeech tts1 = firstWrapper.getTTS();

        TTSWrapper secondWrapper = TTSWrapper.getTestInstance(context, listener, sharedPrefs);
        TextToSpeech tts2 = secondWrapper.getTTS();

        // both references should contain the same Object
        Assert.assertTrue(firstWrapper == secondWrapper);

        //the tts instance should not have been changed
        Assert.assertTrue(tts1 == tts2);
    }

    @Test
    public void testSingletonContextChange() {
        TTSWrapper firstWrapper = TTSWrapper.getTestInstance(context, listener, sharedPrefs);
        TextToSpeech tts1 = firstWrapper.getTTS();

        TTSWrapper secondWrapper = TTSWrapper.getTestInstance(mock(Context.class), listener, sharedPrefs);
        TextToSpeech tts2 = secondWrapper.getTTS();

        // both references should contain the same Object
        Assert.assertTrue(firstWrapper == secondWrapper);

        // because of the new context, the TextToSpeech instance should have changed
        Assert.assertFalse(tts1 == tts2);
    }

}
