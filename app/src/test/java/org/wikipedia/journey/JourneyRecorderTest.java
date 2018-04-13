package org.wikipedia.journey;

import android.content.Context;

import static org.junit.Assert.*;

import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.wikipedia.page.PageProperties;

/**
 * Created by Fred on 2018-04-03.
 */


public class JourneyRecorderTest {

    private PageProperties page1;
    private PageProperties page2;


    @Before
    public void setUp() {
        JourneyRecorder.reset();

        page1 = mock(PageProperties.class);
        page2 = mock(PageProperties.class);

        when(page1.getPageId()).thenReturn(1);
        when(page2.getPageId()).thenReturn(2);
    }

    @Test
    public void testJourneySingleton() {
        JourneyRecorder j1 = JourneyRecorder.getInstance(mock(Context.class));
        JourneyRecorder j2 = JourneyRecorder.getInstance(mock(Context.class));

        assertEquals(j1, j2);
    }

    @Test
    public void testJourneyStart() {
        JourneyRecorder jr = JourneyRecorder.getInstance(mock(Context.class));
        PageProperties page = mock(PageProperties.class);
        jr.startJourney(page);

        Visit root = jr.getRoot();

        assertEquals(root.getPageInfo(), page);
        assertEquals(root.getSubVisits().size(), 0);

        Visit currentVisit = jr.getCurrentVisit();

        assertEquals(root, currentVisit);
    }

    @Test
    public void testJourneyFirstVisitPage() {
        JourneyRecorder jr = spy(JourneyRecorder.getInstance(mock(Context.class)));
        PageProperties page = mock(PageProperties.class);

        jr.visitPage(page);

        jr.visitPage(page);

        verify(jr, times(1)).startJourney(page);

    }

    @Test
    public void testJourneySecondVisitPage() {
        JourneyRecorder jr = JourneyRecorder.getInstance(mock(Context.class));
        PageProperties page1 = mock(PageProperties.class);
        PageProperties page2 = mock(PageProperties.class);

        when(page1.getPageId()).thenReturn(1);
        when(page2.getPageId()).thenReturn(2);



        jr.visitPage(page1);

        Visit firstVisit = jr.getCurrentVisit();

        jr.visitPage(page2);

        Visit secondVisit = jr.getCurrentVisit();

        assertEquals(firstVisit.getSubVisits().get(0), secondVisit);
        assertEquals(secondVisit.getPageInfo(), page2);
    }

    @Test
    public void testJourneyLeaveInnerPage() {
        JourneyRecorder jr = JourneyRecorder.getInstance(mock(Context.class));


        jr.visitPage(page1);

        Visit firstVisit = jr.getCurrentVisit();

        jr.visitPage(page2);

        assertEquals(jr.getPageStack().peek(), firstVisit);

        jr.leavePage();

        assertTrue(jr.getPageStack().empty());
        assertEquals(firstVisit, jr.getCurrentVisit());

    }

    @Test
    public void testJourneyLeaveRootPage() {
        JourneyRecorder jr = spy(JourneyRecorder.getInstance(mock(Context.class)));
        doNothing().when(jr).persist();

        PageProperties page1 = mock(PageProperties.class);

        jr.visitPage(page1);

        jr.leavePage();

        assertTrue(jr.getRoot() == null);
        verify(jr).persist();
    }

}
