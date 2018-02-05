package org.wikipedia.texttospeech;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.speech.tts.TextToSpeech;

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

    private  TTSWrapper (Context context, TextToSpeech.OnInitListener listener) {
        contextID = context.toString();
        tts = new TextToSpeech(context, listener);
        //TODO: Load Preferences
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
            instance.tts = (new TextToSpeech(context, listener));
        }

        return instance;
    }

    public void speak(String text) {
        tts.speak(text, TextToSpeech.QUEUE_ADD, null,"" + requestCounter++ );
    }

    public void stop() {
        tts.stop();
    }

}
