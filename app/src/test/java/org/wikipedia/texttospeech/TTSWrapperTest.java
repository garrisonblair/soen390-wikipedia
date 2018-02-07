package org.wikipedia.texttospeech;

/**
 * Created by Fred on 2018-02-05.
 */
import android.content.Context;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;

import org.mockito.runners.MockitoJUnitRunner;
import org.wikipedia.test.TestRunner;



@RunWith(MockitoJUnitRunner.class)
public class TTSWrapperTest {

    @Mock
    TextToSpeech tts = mock(TextToSpeech.class);

    @Mock
    Context context = mock(Context.class);

    @Mock
    TextToSpeech.OnInitListener listener = mock(TextToSpeech.OnInitListener.class);

    @InjectMocks
    TTSWrapper ttsWrapper = TTSWrapper.getInstance(context, listener);;

    @Before
    public void setUp() throws Throwable {
        ttsWrapper.setTTS(tts);
        when(context.toString()).thenReturn("context");
    }

    @Test
    public void testSpeak() {
        ttsWrapper.speak("Test");
        verify(tts).speak(eq("Test"), eq(TextToSpeech.QUEUE_ADD), eq(null),  anyString());
    }

    @Test
    public void testGetVoices() {
        ttsWrapper.getVoices();
        verify(tts).getVoices();
    }
}
