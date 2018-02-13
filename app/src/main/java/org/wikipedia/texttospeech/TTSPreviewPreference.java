package org.wikipedia.texttospeech;

import android.content.Context;
import android.support.v7.preference.PreferenceViewHolder;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.wikipedia.R;

/**
 * Created by Fred on 2018-02-12.
 */

public class TTSPreviewPreference extends android.support.v7.preference.Preference {

    private TTSWrapper tts;

    public TTSPreviewPreference(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        setWidgetLayoutResource(R.layout.tts_preview_layout);
        setLayoutResource(R.layout.tts_preview_layout);

        tts = TTSWrapper.getInstance(context, null);
    }

    @Override
    public void onBindViewHolder(PreferenceViewHolder holder) {
        Button ttsPreviewBtn = (Button) holder.findViewById(R.id.tts_preview_btn);

        ttsPreviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tts.speak("Test String");
            }
        });
    }

    public void shutdownTTS() {
        tts.shutdown();
    }
}
