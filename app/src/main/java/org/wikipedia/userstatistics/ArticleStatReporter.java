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

    private static final double TO_SEC = 1000.00;
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

        checkAchievements(context);
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public long getTimeSpent()  {
        return timeSpent;
    }

    //Check if user obtained any new achievements at the end of article visit.
    private void checkAchievements(Context context) {
        AchievementChecker checker = new AchievementChecker(context);
        ArticleStatCalculator calculator = new ArticleStatCalculator(context);
        double totalReadingTime = ((double) calculator.getTotalTimeSpentReading()) / TO_SEC;
        double thisReadingTime = ((double) timeSpent) / TO_SEC;
        int totalArticleVisits = calculator.getTotalArticlesRead();

        for (AchievementsList achievement: AchievementsList.values()) {
            if (achievement.getCategory().equals("TotalTimeSpent")) {
                checker.check(achievement, totalReadingTime);
            } else if (achievement.getCategory().equals("TimeSpent")) {
                checker.check(achievement, thisReadingTime);
            } else if (achievement.getCategory().equals("Read")) {
                checker.check(achievement, totalArticleVisits);
            }
        }
    }

}
