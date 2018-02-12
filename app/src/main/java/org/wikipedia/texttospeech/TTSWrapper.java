package org.wikipedia.texttospeech;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.speech.tts.Voice;
import android.support.v7.preference.PreferenceManager;

import org.apache.commons.lang3.LocaleUtils;
import org.wikipedia.settings.SettingsActivity;

import java.util.Locale;
import java.util.Set;



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
        loadPreferences(context);
    }

    //get preferences from settings stored in xml
    public void loadPreferences(Context context) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        String languagePreference = sharedPref.getString("preference_key_language_tts", "english");
        String voicePreference = sharedPref.getString("preference_key_voice_tts", "");
        float pitchPreference = sharedPref.getFloat("preference_key_pitch_tts", 0);
        float speechRatePreference = sharedPref.getFloat("preference_key_speed_tts", 0);
        int queueModePreference = sharedPref.getInt("preference_key_queue_tts", 0);

        tts.setLanguage(LocaleUtils.toLocale(languagePreference));
//        tts.setVoice();
        tts.setPitch(pitchPreference);
        tts.setSpeechRate(speechRatePreference);

        if (queueModePreference == 0){
            this.queueMode = false;
        } else {
            this.queueMode = true;
        }
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
            INSTANCE.tts.shutdown();
            INSTANCE.instantiateTextToSpeech(context, listener);
        }

        return INSTANCE;
    }

    public void speak(String text) {
        int mode = TextToSpeech.QUEUE_FLUSH;

        if (queueMode) {
            mode = TextToSpeech.QUEUE_ADD;
        }

        tts.speak(text, mode, null,"" + requestCounter++ );
    }

    public void stop() {
        tts.stop();
    }



    public Set<Voice> getVoices() {
        return tts.getVoices();
    }

    public void setLanguage(Locale locale) {
        tts.setLanguage(locale);
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

}
