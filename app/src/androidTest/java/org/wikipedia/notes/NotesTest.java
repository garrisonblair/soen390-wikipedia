package org.wikipedia.notes;

import android.app.Activity;
import android.app.Fragment;
import android.app.Instrumentation;
import android.content.Intent;
import android.net.Uri;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.view.KeyEvent;

import org.apache.commons.lang3.StringUtils;
import org.junit.Rule;
import org.junit.Test;
import org.wikipedia.R;
import org.wikipedia.main.MainActivity;
import org.wikipedia.search.SearchFragment;
import org.wikipedia.search.SearchInvokeSource;
import org.wikipedia.views.CabSearchView;

import static android.content.Intent.ACTION_PICK;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.action.ViewActions.typeTextIntoFocusedView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;

public class NotesTest {

    private static final String TEST_INPUT = "foobar";

    @Rule
    public IntentsTestRule<MainActivity> intentsRule = new IntentsTestRule<>(MainActivity.class);

    @Test
    public void openNotesInArticle() {

        // Click to open search fragment
        onView(withId(R.id.search_container)).perform(click());

        // Input text in search bar
        onView(withId(R.id.search_bar)).check(matches(isDisplayed()));
        onView(withId(R.id.search_src_text)).perform(typeText(TEST_INPUT));

        // Wait for search response
        try{
            Thread.sleep(3000);
        }
        catch(InterruptedException e){

        }

        // Select first item
        onData(anything()).inAdapterView(withId(R.id.search_results_list)).atPosition(0).perform(click());

        // Open notes
        onView(withId(R.id.page_toolbar_button_notes)).perform(click());

        // Wait
        try{
            Thread.sleep(3000);
        }
        catch(InterruptedException e){

        }

        // Validate that the notes fragment is displayed
        onView(withId(R.id.fragment_notes)).check(matches(isDisplayed()));
    }
}
