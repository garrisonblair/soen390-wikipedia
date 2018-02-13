package org.wikipedia.page;
import android.speech.tts.TextToSpeech;

import org.junit.Test;
import org.wikipedia.bridge.CommunicationBridge;
import org.wikipedia.language.AppLanguageLookUpTable;
import org.wikipedia.page.shareafact.ShareHandler;

import java.util.Locale;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.*;

/** Unit tests for TTS Language. */
public class TTSLanguageTest {

    @Test
    public void testGetLocale() throws Throwable{
        Page page = new Page()
        PageFragment fragment = new PageFragment();
        PageActivity activity = fragment.getActivity();
        ShareHandler handler = new ShareHandler(PageFragment.class, CommunicationBridge.class);

        //Test if the system passes a language to it, it will return the Locale
        Locale loc1 = page.getLocaleForTTS("Japanese");
        assertEquals(loc1.getCountry(),"JP");

        Locale loc2 = page.getLocaleForTTS("Spanish");
        assertEquals(loc2.getCountry(),"PA");
    }
    @Test
    public void testSetLanguageName(){
        PageActivity page = new PageActivity();

        //Different types of written language Chinese are set to be Chinese
        String language = page.setLanguageName("Traditional Chinese");
        assertEquals(language,"Chinese");

        //Wiki languages do not contain different type of French, so it still returns French
        language = page.setLanguageName("French");
        assertEquals(language,"French");
    }
}
