package org.wikipedia.userstatistics;

import java.util.Date;

public class StatReporter {

    private int articleId;
    private long timeSpent;
    private Date start;
    private Date pause;
    private Date resume;
    private Date end;

    private static final long DIV_BY_TO_GET_MINUTES = 60000;

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

    public void endVisit() {
        end = new Date();
        if (pause != null && resume != null) {
            long diff = pause.getTime() - start.getTime() + end.getTime() - resume.getTime();
            timeSpent = diff / DIV_BY_TO_GET_MINUTES;
        } else {
            long diff = end.getTime() - start.getTime();
            timeSpent = diff / DIV_BY_TO_GET_MINUTES;
        }
        // TODO: save timeSpent to db
    }

}
