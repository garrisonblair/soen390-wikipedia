package org.wikipedia.page;
import org.junit.Test;
import java.util.Locale;

import static junit.framework.Assert.assertEquals;

/** Unit tests for TTS Language. */
public class TTSLanguageTest {

    @Test
    public void testGetLocale() throws Throwable{
        PageActivity page = new PageActivity();

        //Test if the system passes a language to it, it will return the Locale
        Locale loc1 = page.getLocaleForTTS("Japanese");
        assertEquals(loc1.getCountry(),"JP");

        Locale loc2 = page.getLocaleForTTS("Spanish");
        assertEquals(loc2.getCountry(),"PA");
    }

}
