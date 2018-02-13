package org.wikipedia.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.speech.tts.UtteranceProgressListener;
import android.speech.tts.Voice;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.wikipedia.R;
import org.wikipedia.WikipediaApp;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import static org.apache.commons.lang3.StringUtils.defaultString;

import android.speech.tts.TextToSpeech;
import org.wikipedia.texttospeech.TTSWrapper;

/**
 * Created by garrisonblair on 2018-02-06.
 */

public class TTSVoicePreferenceDialog extends AppCompatDialog {

    private ListView voicesList;
    private final TTSWrapper tts;
    private Set<Voice> voices;

    @NonNull
    private ArrayList<String> voiceNames;


    @NonNull
    private final WikipediaApp app;

    public TTSVoicePreferenceDialog(Context context, boolean b, TTSWrapper tts_instance) {
        super(context);
        setContentView(R.layout.dialog_preference_tts_voices);

        app = WikipediaApp.getInstance();
        tts = tts_instance;
        voices = tts.getVoices();
        Iterator<Voice> it = voices.iterator();
        voiceNames = new ArrayList<String>();

        while (it.hasNext()) {
            voiceNames.add(it.next().getName());
        }
        
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        voicesList = (ListView) findViewById(R.id.preference_voices_list);

        // Creating and setting array adapter for ListView
        voicesList.setAdapter(new ArrayAdapter<String>(getContext(), /*R.layout.dialog_preference_tts_voices, */ R.layout.simple_row, voiceNames));

        // Adding Listener on Item click to the adapter view
        AdapterView.OnItemClickListener ttsClickedHandler = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Set Voice selection in settings
//                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());
//                sharedPref.edit()

            }
        };
    }
}






