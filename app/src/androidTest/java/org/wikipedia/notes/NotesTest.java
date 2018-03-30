package org.wikipedia.notes;

import android.support.test.rule.ActivityTestRule;
import android.test.mock.MockContext;

import org.junit.Rule;
import org.junit.Test;
import org.wikipedia.R;
import org.wikipedia.notebook.Note;
import org.wikipedia.notebook.NoteReferenceService;
import org.wikipedia.page.PageActivity;

import java.util.List;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.anything;

public class NotesTest {

    private static final String TEST_INPUT = "foobar";
    private static final int articleId = 11178;
    @Rule
    public ActivityTestRule activityRule = new ActivityTestRule<>(
            PageActivity.class,
            true,    // initialTouchMode
            true);  // launchActivity.

    // Testing that the different fragments related to notes are displayed when called.
    // Doing the tests for multiple fragment in one method because of the waits that we need to implement.
    // If we did different methods for each fragment, it will take a lot of time to test everything.
    @Test
    public void openNotesInArticle() {
        addNoteForTest();
        onView(withId(R.id.page_toolbar_button_search)).perform(click());

        // Input text in search bar
        onView(withId(R.id.search_bar)).check(matches(isDisplayed()));
        onView(withId(R.id.search_src_text)).perform(typeText(TEST_INPUT));
        waitDisplay();

        // Select first item
        onData(anything()).inAdapterView(withId(R.id.search_results_list)).atPosition(0).perform(click());

        // Open notes
        onView(withId(R.id.page_toolbar_button_notes)).perform(click());
        waitDisplay();

        // TEST 1
        // Validate that the notes fragment is displayed
        onView(withId(R.id.fragment_notes)).check(matches(isDisplayed()));

        // Select first note
        onData(anything()).inAdapterView(withId(R.id.notes_list)).atPosition(0).perform(click());
        waitDisplay();

        // TEST 2
        // Validate that the NotesCommentFragment get opened
        onView(withId(R.id.note_comment)).perform(click());
        waitDisplay();
        onView(withId(R.id.fragment_notes_comment)).check(matches(isDisplayed()));

        // TEST 3
        // Validate that the NotesEditFragment get opened
        onView(withId(R.id.notes_comment_back_button)).perform(click());
        waitDisplay();
        onView(withId(R.id.note_edit)).perform(click());
        waitDisplay();
        onView(withId(R.id.fragment_notes_edit)).check(matches(isDisplayed()));

    }

    public void waitDisplay() {
        try{
            Thread.sleep(1000);
        }
        catch(InterruptedException e){

        }
    }

    // Adds a note for the tested article so that we can perform the UI checks. Only add the note if there are no other notes.
    public void addNoteForTest() {
        NoteReferenceService service = new NoteReferenceService(new MockContext());
        service.getAllArticleNotes(articleId, new NoteReferenceService.GetNotesCallback() {
            @Override
            public void afterGetNotes(List<Note> notes) {
                if (notes.size() == 0) {
                    Note note = new Note(articleId, TEST_INPUT, "This is a note created for testing purpose. You can delete it.");
                    service.addNote(note, new NoteReferenceService.SaveCallback() {
                        @Override
                        public void afterSave() {

                        }
                    });
                }
            }
        });

    }
}
