//package org.wikipedia.ttsvoice;
//
//import android.support.test.runner.AndroidJUnit4;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import org.wikipedia.R;
//
//import static android.support.test.espresso.Espresso.onData;
//import static android.support.test.espresso.Espresso.onView;
//import static android.support.test.espresso.action.ViewActions.click;
//import static android.support.test.espresso.matcher.ViewMatchers.withId;
//import static org.hamcrest.Matchers.allOf;
//import static org.hamcrest.Matchers.instanceOf;
//import static org.hamcrest.Matchers.is;
//
///**
// * Created by garrisonblair on 2018-02-13.
// */
//
//@RunWith(AndroidJUnit4.class)
//public class TTSVoicePreferenceDialogTest {
//
//    @Test
//    public void testVoiceListView() {
//
//        onView(withId(R.id.preference_voices_list)).perform(click());
//        onData(allOf(is(instanceOf(String.class)), is("Voice 1"))).perform(click());
//    }
//}
