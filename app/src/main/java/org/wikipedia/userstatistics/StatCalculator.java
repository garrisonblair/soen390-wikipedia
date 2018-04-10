package org.wikipedia.userstatistics;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import android.content.Context;
import android.content.pm.PackageManager;

import org.wikipedia.database.room.AppDatabase;
import org.wikipedia.statistics.database.ArticleVisitDao;
import org.wikipedia.statistics.database.ArticleVisitEntity;

public class StatCalculator {

    private Context context;
    private AppDatabase db;
    private ArticleVisitDao articleVisitDao;
    private List<ArticleVisitEntity> visitedArticles;
    private List<Integer> uniqueVisitedArticles;

    private long totalTimeSpentReading;
    private int longestReadArticleId;

    private int totalNotes;
    private int totalArticlesWithNotes;

    private int textSearches;
    private int gallerySearches;
    private int cameraSearches;

    private int ttsUses;

    public StatCalculator(Context context) {
        this.context = context;
        this.db = AppDatabase.getInstance(context);
        this.articleVisitDao = db.articleVisitDao();

        visitedArticles = articleVisitDao.getTotalUniqueVisits();

        totalTimeSpentReading = 0;
        long longestTime = 0;
        uniqueVisitedArticles = new ArrayList<>();

        for (ArticleVisitEntity article: visitedArticles) {
            totalTimeSpentReading += article.getTimeSpentReading();
            if (article.getTimeSpentReading() > longestTime) {
                longestTime = article.getTimeSpentReading();
                longestReadArticleId = article.getArticleId();
            }
            if (!(uniqueVisitedArticles.contains(article.getArticleId()))) {
                uniqueVisitedArticles.add(article.getArticleId());
            }
        }
    }

    public long getTotalTimeSpentReading() {
        return totalTimeSpentReading;
    }

    public long getAverageTimeSpentReading() {
        return totalTimeSpentReading / visitedArticles.size();
    }

    public long getDailyTimeSpentReading() throws PackageManager.NameNotFoundException {
        long installed = context
                .getPackageManager()
                .getPackageInfo(context.getPackageName(), 0)
                .firstInstallTime;

        long timeUsed = (new Date().getTime() - installed);
        long days = TimeUnit.MILLISECONDS.toDays(timeUsed);

        return totalTimeSpentReading / days;
    }

    public int getLongestReadArticleId() {
        return longestReadArticleId;
    }

    public int getTotalArticlesRead() {
        return visitedArticles.size();
    }

    public int getUniqueArticlesRead() {
        return uniqueVisitedArticles.size();
    }
}
