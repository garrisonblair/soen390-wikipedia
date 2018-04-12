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

public class ArticleStatCalculator {

    private Context context;
    private AppDatabase db;
    private ArticleVisitDao articleVisitDao;
    private List<ArticleVisitEntity> visitedArticles;
    private List<Integer> uniqueVisitedArticles;

    private long totalTimeSpentReading;
    private String longestReadArticleTitle;
    private long longestReadArticleTime;

    public ArticleStatCalculator(Context context, AppDatabase database) {
        this.context = context;
        this.db = database;
        this.articleVisitDao = db.articleVisitDao();

        visitedArticles = articleVisitDao.getTotalUniqueVisits();

        totalTimeSpentReading = 0;
        longestReadArticleTime = 0;
        uniqueVisitedArticles = new ArrayList<>();

        for (ArticleVisitEntity article: visitedArticles) {
            totalTimeSpentReading += article.getTimeSpentReading();
            if (article.getTimeSpentReading() > longestReadArticleTime) {
                longestReadArticleTime = article.getTimeSpentReading();
                longestReadArticleTitle = article.getArticleTitle();
            }
            if (!(uniqueVisitedArticles.contains(article.getArticleId()))) {
                uniqueVisitedArticles.add(article.getArticleId());
            }
        }
    }

    public ArticleStatCalculator(Context context) {
        this(context, AppDatabase.getInstance(context));
    }

    public long getTotalTimeSpentReading() {
        return totalTimeSpentReading;
    }

    public long getAverageTimeSpentReading() {
        if (visitedArticles.size() > 0) {
            return totalTimeSpentReading / visitedArticles.size();
        } else {
            return 0;
        }
    }

    public long getDailyTimeSpentReading() throws PackageManager.NameNotFoundException {
        long installed = context
                .getPackageManager()
                .getPackageInfo(context.getPackageName(), 0)
                .firstInstallTime;

        long timeUsed = (new Date().getTime() - installed);
        long days = TimeUnit.MILLISECONDS.toDays(timeUsed);

        if (days > 0) {
            return totalTimeSpentReading / days;
        } else {
            return 0;
        }
    }

    public String getLongestReadArticleTitle() {
        return longestReadArticleTitle;
    }

    public long getLongestReadArticleTime() {
        return longestReadArticleTime;
    }

    public int getTotalArticlesRead() {
        return visitedArticles.size();
    }

    public int getUniqueArticlesRead() {
        return uniqueVisitedArticles.size();
    }
}
