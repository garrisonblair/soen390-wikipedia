package org.wikipedia.userstatistics;

import android.os.Handler;

import org.junit.Assert;
import org.junit.Test;

public class StatReporterTest {

    @Test
    public void testEndVisit() throws InterruptedException {
        // Visit article for 3 seconds
        StatReporter statReporter = new StatReporter();
        long visitTime = 3;

        statReporter.enterArticle(0);
        Thread.sleep(3000);
        statReporter.endVisit();

        // Verify that timeSpent is 3 seconds
        Assert.assertEquals(visitTime, statReporter.getTimeSpent());

        // Visit article for 3 seconds, pause for a second, then resume for another 3 seconds
        statReporter = new StatReporter();
        visitTime = 6;

        statReporter.enterArticle(0);
        Thread.sleep(3000);
        statReporter.pauseVisit();
        Thread.sleep(1000);
        statReporter.resumeVisit();
        Thread.sleep(3000);
        statReporter.endVisit();

        // Verify that timeSpent is 6 seconds
        Assert.assertEquals(visitTime, statReporter.getTimeSpent());
    }
}
