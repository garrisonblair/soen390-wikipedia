package org.wikipedia.page.listeners;

import android.speech.tts.UtteranceProgressListener;
import android.view.View;

import org.wikipedia.page.PageActivity;

public class HideStopButtonOnDoneListener extends UtteranceProgressListener {
    private PageActivity pageActivity;
    public HideStopButtonOnDoneListener(PageActivity pageActivity){
        this.pageActivity = pageActivity;
    }
    @Override
    public void onStart(String s) {

    }

    @Override
    public void onDone(String s) {
        pageActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                pageActivity.getStopButton().setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public void onError(String s) {

    }
    public void superSedeOnDone(PageActivity activity){
        activity.getStopButton().setVisibility(View.INVISIBLE);
    }
}