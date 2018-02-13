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

import static org.mockito.Matchers.anyFloat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;

/**
 * Created by Fred on 2018-02-05.
 * Tests TTSWrapper functionality
 */
@RunWith(MockitoJUnitRunner.class)
public class TTSPreferencesLoaderTest {

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
        TestUtils.setFinalStatic(Build.VERSION.class.getField("SDK_INT"), 22);
        ttsWrapper = TTSWrapper.getTestInstance(context, listener, sharedPrefs);
        ttsWrapper.setTTS(tts);
        Mockito.when(sharedPrefs.getBoolean("ttsQueue", false)).thenReturn(false);
    }

    @Test
    public void testGetValidPreferences() throws Exception {
        TextToSpeech tts1 = mock(TextToSpeech.class);
        TTSWrapper wrapper = TTSWrapper.getTestInstance(context, listener, sharedPrefs);
        wrapper.setTTS(tts1);
        reset(sharedPrefs);
        wrapper.loadPreferences();

//        verify(sharedPrefs).getString("preference_key_voice_tts", "");
        verify(sharedPrefs).getInt("ttsPitch", 0);
        verify(sharedPrefs).getInt("ttsSpeed", 0);
        verify(sharedPrefs).getBoolean("ttsQueue", false);

//        verify(tts1).setVoice();
        verify(tts1).setPitch(anyFloat());
        verify(tts1).setSpeechRate(anyFloat());
        Assert.assertEquals(false,wrapper.getQueueMode());


    }

}
