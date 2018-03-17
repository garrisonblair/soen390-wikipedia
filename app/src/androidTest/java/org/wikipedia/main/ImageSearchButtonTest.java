package org.wikipedia.main;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.rule.IntentsTestRule;

import net.hockeyapp.android.utils.PrefsUtil;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.wikipedia.R;
import org.wikipedia.settings.Prefs;

import static android.content.Intent.ACTION_PICK;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


public class ImageSearchButtonTest {
    private static final String TEST_URI = "foobar";

    @Rule
    public IntentsTestRule<MainActivity> intentsRule = new IntentsTestRule<>(MainActivity.class);

    @BeforeClass
    public static void setUp() {
        Prefs.setInitialOnboardingEnabled(false);
        Prefs.setFeaturesOnboardingEnabled(false);

    }
    //Buttons in the feed fragment
    @Ignore
    @Test
    public void onCameraImageSearchClick() {

        Bitmap icon = BitmapFactory.decodeResource(
                InstrumentationRegistry.getTargetContext().getResources(),
                R.mipmap.launcher);

        // Build a result to return from the Camera app
        Intent resultData = new Intent();
        resultData.putExtra("data", icon);
        Instrumentation.ActivityResult result = new Instrumentation.ActivityResult(Activity.RESULT_OK, resultData);

        // Stub out the Camera. When an intent is sent to the Camera, this tells Espresso to respond
        // with the ActivityResult we just created
        intending(hasAction(MediaStore.ACTION_IMAGE_CAPTURE)).respondWith(result);

        // Now that we have the stub in place, click on the button in our app that launches into the Camera
        onView(withId(R.id.image_search_open_camera_button)).perform(click());



        // We can also validate that an intent resolving to the "camera" activity has been sent out by our app
        intended(hasAction(MediaStore.ACTION_IMAGE_CAPTURE));
    }

    @Test
    public void onGallerySearchClick() {
        Bitmap icon = BitmapFactory.decodeResource(
                InstrumentationRegistry.getTargetContext().getResources(),
                R.mipmap.launcher);

        Intent resultData = new Intent();
        resultData.setData(Uri.parse(TEST_URI));

        Instrumentation.ActivityResult result = new Instrumentation.ActivityResult(Activity.RESULT_OK, resultData);

        intending(hasAction(ACTION_PICK)).respondWith(result);

        onView(withId(R.id.gallery_search_button)).perform(click());

        intended(hasAction(ACTION_PICK));
    }

    //Buttons in the search fragment
    @Ignore
    @Test
    public void onCameraImageSearchFragmentClick() {
        Bitmap icon = BitmapFactory.decodeResource(
                InstrumentationRegistry.getTargetContext().getResources(),
                R.mipmap.launcher);

        onView(withId(R.id.feed_search_field)).perform(click());

        // Build a result to return from the Camera app
        Intent resultData = new Intent();
        resultData.putExtra("data", icon);
        Instrumentation.ActivityResult result = new Instrumentation.ActivityResult(Activity.RESULT_OK, resultData);

        // Stub out the Camera. When an intent is sent to the Camera, this tells Espresso to respond
        // with the ActivityResult we just created
        intending(hasAction(MediaStore.ACTION_IMAGE_CAPTURE)).respondWith(result);

        // Now that we have the stub in place, click on the button in our app that launches into the Camera
        onView(withId(R.id.search_open_camera_button)).perform(click());

        // We can also validate that an intent resolving to the "camera" activity has been sent out by our app
        intended(hasAction(MediaStore.ACTION_IMAGE_CAPTURE));
    }

    @Test
    public void onGallerySearchFragmentClick() {
        Bitmap icon = BitmapFactory.decodeResource(
                InstrumentationRegistry.getTargetContext().getResources(),
                R.mipmap.launcher);

        onView(withId(R.id.feed_search_field)).perform(click());


        Intent resultData = new Intent();
        resultData.setData(Uri.parse(TEST_URI));

        Instrumentation.ActivityResult result = new Instrumentation.ActivityResult(Activity.RESULT_OK, resultData);

        intending(hasAction(ACTION_PICK)).respondWith(result);

        onView(withId(R.id.search_gallery_button)).perform(click());

        intended(hasAction(ACTION_PICK));
    }
}