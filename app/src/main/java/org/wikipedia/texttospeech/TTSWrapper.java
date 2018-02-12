package org.wikipedia.texttospeech;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.support.v7.preference.PreferenceManager;

import org.wikipedia.settings.SettingsActivity;

import java.util.Locale;
import java.util.Set;



/**
 * Created by Fred on 2018-02-05.
 * Abstraction on top of the Android TextToSpeech class.  Eliminates certain details and handles loading the Preferences.
 */

@TargetApi(21)
public class TTSWrapper {

    private static TTSWrapper instance;


    private TextToSpeech tts;

    // Store Context.toString() of last use.  If a different Context tries to use it the instance needs to be reinstantiated.
    private String contextID;

    //Identifies requests to TTS instance
    private int requestCounter;

    private String language;
    private String voice;
    private String pitch;
    private String speed;
    private boolean queueMode;

    private  TTSWrapper (Context context, TextToSpeech.OnInitListener listener) {
        contextID = context.toString();
        tts = new TextToSpeech(context, listener);
        loadPreferences(context);
    }

    //get preferences from settings stored in xml
    private void loadPreferences(Context context) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        this.language = sharedPref.getString(SettingsActivity.KEY_, "");
        this.voice = sharedPref.getString(SettingsActivity.KEY_, "");
        this.pitch = sharedPref.getString(SettingsActivity.KEY_, "");
        this.speed = sharedPref.getString(SettingsActivity.KEY_, "");
        this.queueMode = sharedPref.getString(SettingsActivity.KEY_, "");

    }

    public static TTSWrapper getInstance(Context context, TextToSpeech.OnInitListener listener) {

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return null;
        }

        if(instance == null) {
            instance = new TTSWrapper(context, listener);
        }

        // If requesting TTS from a different context, reinstantiate TTS
        if(!context.toString().equals(instance.contextID)) {
            instance.tts.shutdown();
            instance.tts = new TextToSpeech(context, listener);
        }

        return instance;
    }

    public void speak(String text) {
        int mode = TextToSpeech.QUEUE_FLUSH;

        if(queueMode) {
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

}
