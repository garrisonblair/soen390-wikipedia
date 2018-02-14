package org.wikipedia.texttospeech;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.speech.tts.Voice;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Set;

import static java.util.Collections.sort;


/**
 * Created by Fred on 2018-02-05.
 * Abstraction on top of the Android TextToSpeech class.  Eliminates certain details and handles loading the Preferences.
 */

@TargetApi(21)
public final class TTSWrapper {

    private static TTSWrapper INSTANCE;


    private TextToSpeech tts;

    // Store Context.toString() of last use.  If a different Context tries to use it the INSTANCE needs to be reinstantiated.
    private String contextID;

    //Identifies requests to TTS INSTANCE
    private int requestCounter;

    private boolean queueMode;

    private  TTSWrapper(Context context, UtteranceProgressListener listener) {
        contextID = context.toString();

        this.instantiateTextToSpeech(context, listener);
        //TODO: Load Preferences
    }

    public static TTSWrapper getInstance(Context context, UtteranceProgressListener listener) {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return null;
        }

        if (INSTANCE == null) {
            INSTANCE = new TTSWrapper(context, listener);
        }

        // If requesting TTS from a different context, reinstantiate TTS
        if (!context.toString().equals(INSTANCE.contextID)) {
            INSTANCE.instantiateTextToSpeech(context, listener);
        }

        return INSTANCE;
    }

    public void speak(String text) {

        if (text.equals("")) {
            return;
        }

        int mode = TextToSpeech.QUEUE_FLUSH;

        if (queueMode) {
            mode = TextToSpeech.QUEUE_ADD;
        }

        tts.speak(text, mode, null, "" + requestCounter++);
    }

    public void stop() {
        tts.stop();
    }

    public void shutdown() {
        tts.shutdown();
    }

    public Set<Voice> getVoices() {
        return tts.getVoices();
    }

    public void setLanguage(Locale locale) {
        tts.setLanguage(locale);
    }

    public String getTTSLanguage() {
        return tts.getLanguage().getDisplayLanguage();
    }

    public Set<Locale> getLanguages() {
        return tts.getAvailableLanguages();
    }

    public void setQueueMode(boolean queueMode) {
        this.queueMode = queueMode;
    }

    // tts getter and setter only for testing purposes
    public TextToSpeech getTTS() {
        return tts;
    }

    public void setTTS(TextToSpeech tts) {
        this.tts = tts;
    }

    private void instantiateTextToSpeech(Context context, UtteranceProgressListener listener) {
        if (listener != null) {
            tts = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {
                    if (status != TextToSpeech.ERROR) {
                        tts.setOnUtteranceProgressListener(listener);
                    }
                }
            });
        } else {
            tts = new TextToSpeech(context, null);
        }
    }

    public int isTTSLanguageAvailable(Locale locale) {
        return tts.isLanguageAvailable(locale);
    }

    public ArrayList getTTSOptionList() {
        ArrayList<String> ttsLanguages = new ArrayList<>();
        for (Locale locale : this.getLanguages()) {
            String country = locale.getDisplayCountry();
            if (locale.getDisplayCountry().equals("")) {
                country = "None";
            }
            ttsLanguages.add(locale.getDisplayLanguage() + " : " + country);
        }
        sort(ttsLanguages);
        return ttsLanguages;
    }


    //set the TTS language
    public boolean setTTSLanguage(Locale locale) {
        int result = this.isTTSLanguageAvailable(locale);

        boolean isPageLanguage = false;
        if (result == TextToSpeech.LANG_AVAILABLE || result == TextToSpeech.LANG_COUNTRY_AVAILABLE || result == TextToSpeech.LANG_COUNTRY_VAR_AVAILABLE) {
            tts.setLanguage(locale);
            isPageLanguage = true;
        } else if (result == TextToSpeech.LANG_NOT_SUPPORTED) {
            isPageLanguage =  false;
        }
        return isPageLanguage;
    }

    //find the Locale from the given language
    public Locale getLocaleForTTS(String language) {

        Locale locale = Locale.getDefault();
        Locale[] locales = Locale.getAvailableLocales();

        //loop until the matched language found
        for (Locale loc : locales) {
            if (loc.getDisplayLanguage().equals(language)) {
                locale = loc;
                break;
            }
        }
        return locale;
    }

    public boolean isLocaleFound(String language) {
        Locale[] locales = Locale.getAvailableLocales();

        //loop until the matched language found
        for (Locale loc : locales) {
            if (loc.getDisplayLanguage().equals(language)) {
                return true;
            }
        }
        return false;
    }
}
