package org.wikipedia.page;

import android.view.View;
import android.widget.ImageButton;

import org.junit.Test;
import org.mockito.Mock;
import org.wikipedia.offline.Compilation;
import org.wikipedia.page.listeners.HideStopButtonOnDoneListener;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Andres on 2018-02-13.
 */

public class HideStopButtonOnDoneListenerTest {

    @Test
    public void onDoneTest(){
        PageActivity page = mock(PageActivity.class);
        ImageButton stopButton = mock(ImageButton.class);
        when(page.getStopButton()).thenReturn(stopButton);
        HideStopButtonOnDoneListener listener = new HideStopButtonOnDoneListener(page);
        listener.superSedeOnDone(page);
        verify(stopButton).setVisibility(View.INVISIBLE);
    }
}
