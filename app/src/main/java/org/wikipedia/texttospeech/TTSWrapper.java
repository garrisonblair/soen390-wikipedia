package org.wikipedia.texttospeech;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.speech.tts.Voice;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;

import java.util.Locale;
import java.util.Optional;
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

    private SharedPreferences preferences;

    private  TTSWrapper(Context context, UtteranceProgressListener listener, SharedPreferences preferences) {
        contextID = context.toString();
        this.preferences = preferences;

        this.instantiateTextToSpeech(context, listener);
    }

    //get preferences from settings stored in xml
    public void loadPreferences() {
        final int base = 25;
        String voicePreference = preferences.getString("ttsVoice", "");
        int pitchPreference = preferences.getInt("ttsPitch", base);
        int speechRatePreference = preferences.getInt("ttsSpeed", base);
        boolean queueModePreference = preferences.getBoolean("ttsQueue", false);

        Voice voice = this.getVoice(voicePreference);

        tts.setVoice(voice);
        tts.setPitch((float) pitchPreference / base);
        tts.setSpeechRate((float) speechRatePreference / base);
        this.queueMode = queueModePreference;
    }

    public static TTSWrapper getInstance(Context context, UtteranceProgressListener listener) {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return null;
        }

        if (INSTANCE == null) {
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
            INSTANCE = new TTSWrapper(context, listener, sharedPref);
        }

        // If requesting TTS from a different context, reinstantiate TTS
        if (!context.toString().equals(INSTANCE.contextID)) {
            INSTANCE.tts.shutdown();
            INSTANCE.instantiateTextToSpeech(context, listener);
        }

        return INSTANCE;
    }

    public static TTSWrapper getTestInstance(Context context, UtteranceProgressListener listener, SharedPreferences preferences) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return null;
        }

        if (INSTANCE == null) {
            INSTANCE = new TTSWrapper(context, listener, preferences);
        }

        // If requesting TTS from a different context, reinstantiate TTS
        if (!context.toString().equals(INSTANCE.contextID)) {
            INSTANCE.tts.shutdown();
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

    public Voice getVoice(String voiceName) {
        if (voiceName != ""){
            Set<Voice> voices = this.getVoices();
            for (Voice voice : voices) {
                if (voice.getName().equals(voiceName)) {
                    return voice;
                }
            }
        }
        return null;
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

    public boolean getQueueMode() { return this.queueMode; }


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
                        loadPreferences();
                        tts.setOnUtteranceProgressListener(listener);
                    }
                }
            });
        } else {
            tts = new TextToSpeech(context, null);
        }
    }

//    Decouples tests that are dependent on a singleton
    public static void reset() {
        INSTANCE = null;
    }

}
