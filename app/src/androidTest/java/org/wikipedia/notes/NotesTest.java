package org.wikipedia.notes;

import android.support.test.rule.ActivityTestRule;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.wikipedia.R;
import org.wikipedia.page.PageActivity;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.anything;

@Ignore
public class NotesTest {

    private static final String TEST_INPUT = "foobar";

    @Rule
    public ActivityTestRule activityRule = new ActivityTestRule<>(
            PageActivity.class,
            true,    // initialTouchMode
            true);  // launchActivity.

    @Test
    public void openNotesInArticle() {

        onView(withId(R.id.page_toolbar_button_search)).perform(click());

        // Input text in search bar
        onView(withId(R.id.search_bar)).check(matches(isDisplayed()));
        onView(withId(R.id.search_src_text)).perform(typeText(TEST_INPUT));

        // Wait for search response
        try{
            Thread.sleep(1000);
        }
        catch(InterruptedException e){

        }

        // Select first item
        onData(anything()).inAdapterView(withId(R.id.search_results_list)).atPosition(0).perform(click());

        // Open notes
        onView(withId(R.id.page_toolbar_button_notes)).perform(click());

        // Wait
        try{
            Thread.sleep(1000);
        }
        catch(InterruptedException e){

        }

        // Validate that the notes fragment is displayed
        onView(withId(R.id.fragment_notes)).check(matches(isDisplayed()));
    }
    
}
