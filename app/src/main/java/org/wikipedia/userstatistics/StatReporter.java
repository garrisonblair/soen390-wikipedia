package org.wikipedia.userstatistics;

import java.util.Date;

public class StatReporter {

    private int articleId;
    private int timeSpent;
    private Date start;
    private Date pause;
    private Date resume;
    private Date end;

    public void enterArticle(int articleId) {
        this.articleId = articleId;
        start = new Date();
    }

    public void pauseVisit() {
        pause = new Date();
    }

    public void resumeVisit() {
        resume = new Date();
    }

}
