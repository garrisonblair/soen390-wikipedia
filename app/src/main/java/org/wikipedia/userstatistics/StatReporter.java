package org.wikipedia.userstatistics;

import android.util.Log;

import java.util.Date;

public class StatReporter {

    private int articleId;
    private long timeSpent;
    private Date start;
    private Date pause;
    private Date resume;

    private static final long DIV_BY_TO_GET_SECONDS = 1000;

    public void enterArticle() {
        start = new Date();
    }

    public void pauseVisit() {
        pause = new Date();
    }

    public void resumeVisit() {
        resume = new Date();
    }

    public void endVisit() {
        Date end = new Date();
        if (pause != null && resume != null) {
            long diff = pause.getTime() - start.getTime() + end.getTime() - resume.getTime();
            timeSpent = diff / DIV_BY_TO_GET_SECONDS;
        } else {
            long diff = end.getTime() - start.getTime();
            timeSpent = diff / DIV_BY_TO_GET_SECONDS;
        }
        Log.i("DEBUG: TIME SPENT", Long.toString(timeSpent));
        Log.i("DEBUG: ARTICLE ID", Integer.toString(articleId));
    }

    public void saveVisit() {
        // TODO: save timeSpent to db
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public long getTimeSpent()  {
        return timeSpent;
    }

}
