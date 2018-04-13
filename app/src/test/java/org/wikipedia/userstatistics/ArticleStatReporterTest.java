package org.wikipedia.userstatistics;

import org.junit.Assert;
import org.junit.Test;

public class ArticleStatReporterTest {

    @Test
    public void testEndVisit() throws InterruptedException {
        // Visit article for 3 seconds
        ArticleStatReporter articleStatReporter = new ArticleStatReporter();
        long visitTime = 3;

        articleStatReporter.enterArticle();
        Thread.sleep(3000);
        articleStatReporter.endVisit();

        // Verify that timeSpent is 3 seconds
        Assert.assertEquals(visitTime, (articleStatReporter.getTimeSpent() / 1000));

        // Visit article for 3 seconds, pause for a second, then resume for another 3 seconds
        articleStatReporter = new ArticleStatReporter();
        visitTime = 6;

        articleStatReporter.enterArticle();
        Thread.sleep(3000);
        articleStatReporter.pauseVisit();
        Thread.sleep(1000);
        articleStatReporter.resumeVisit();
        Thread.sleep(3000);
        articleStatReporter.endVisit();

        // Verify that timeSpent is 6 seconds
        Assert.assertEquals(visitTime, (articleStatReporter.getTimeSpent() / 1000));
    }
}
