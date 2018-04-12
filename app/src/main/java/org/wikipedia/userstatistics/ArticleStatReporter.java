package org.wikipedia.userstatistics;

import android.content.Context;
import android.util.Log;

import org.wikipedia.database.room.AppDatabase;
import org.wikipedia.userstatistics.Database.ArticleVisitDao;
import org.wikipedia.userstatistics.Database.ArticleVisitEntity;

import java.util.Date;

public class ArticleStatReporter {

    private String articleTitle;
    private long timeSpent;
    private Date start;
    private Date pause;
    private Date resume;

    private AppDatabase db;
    private ArticleVisitDao articleVisitDao;

    public ArticleStatReporter() {
    }

    public void enterArticle() {
        start = new Date();

        Log.i("DEBUG: ENTER", "");
    }

    public void pauseVisit() {
        pause = new Date();

        Log.i("DEBUG: PAUSE", "");
    }

    public void resumeVisit() {
        resume = new Date();

        Log.i("DEBUG: RESUME", "");
    }

    public void endVisit() {
        Date end = new Date();
        if (pause != null && resume != null) {
            timeSpent = pause.getTime() - start.getTime() + end.getTime() - resume.getTime();
        } else {
            timeSpent = end.getTime() - start.getTime();

        }
        Log.i("DEBUG: TIME SPENT", Long.toString(timeSpent));
        Log.i("DEBUG: TITLE", articleTitle);
    }

    public void saveVisit(Context context) {
        this.db = AppDatabase.getInstance(context);
        this.articleVisitDao = db.articleVisitDao();

        ArticleVisitEntity articleVisitEntity = new ArticleVisitEntity(articleTitle, timeSpent, start.getTime());
        articleVisitDao.addArticleVisit(articleVisitEntity);
        Log.i("DEBUG", "ARTICLE SAVED");
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public long getTimeSpent()  {
        return timeSpent;
    }

}
