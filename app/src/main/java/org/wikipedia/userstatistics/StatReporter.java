package org.wikipedia.userstatistics;

import android.content.Context;
import android.util.Log;

import org.wikipedia.database.room.AppDatabase;
import org.wikipedia.statistics.database.ArticleVisitDao;
import org.wikipedia.statistics.database.ArticleVisitEntity;

import java.util.Date;

public class StatReporter {

    private int articleId;
    private long timeSpent;
    private Date start;
    private Date pause;
    private Date resume;

    private AppDatabase db;
    private ArticleVisitDao articleVisitDao;

    public StatReporter(Context context) {
        this.db = AppDatabase.getInstance(context);
        this.articleVisitDao = db.articleVisitDao();
    }

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
            timeSpent = pause.getTime() - start.getTime() + end.getTime() - resume.getTime();
        } else {
            timeSpent = end.getTime() - start.getTime();

        }
        Log.i("DEBUG: TIME SPENT", Long.toString(timeSpent));
        Log.i("DEBUG: ARTICLE ID", Integer.toString(articleId));
    }

    public void saveVisit() {
        ArticleVisitEntity articleVisitEntity = new ArticleVisitEntity(articleId, timeSpent, start.getTime());
        articleVisitDao.addArticleVisit(articleVisitEntity);
        Log.i("DEBUG", "ARTICLE SAVED");
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public long getTimeSpent()  {
        return timeSpent;
    }

}
